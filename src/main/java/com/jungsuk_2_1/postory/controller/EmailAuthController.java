package com.jungsuk_2_1.postory.controller;

import com.jungsuk_2_1.postory.dto.EmailAuthDto;
import com.jungsuk_2_1.postory.dto.UserDto;
import com.jungsuk_2_1.postory.service.EmailAuthService;
import com.jungsuk_2_1.postory.service.EmailKeyProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/email")
public class EmailAuthController {
    private EmailAuthService emailAuthService;
    private EmailKeyProvider emailKeyProvider;

    EmailAuthController(EmailAuthService emailAuthService, EmailKeyProvider emailKeyProvider) {
        this.emailAuthService = emailAuthService;
        this.emailKeyProvider = emailKeyProvider;
    }

    @GetMapping("/auth")
    public ResponseEntity<?> emailAuth(@AuthenticationPrincipal String userId) throws Exception {
        //로그인을 해서 토큰을 가지고 있고, 인증메일 받기를 눌렀을 때
        //토큰에서 userId(uuid)를 parsing해서 유저 정보를 찾는다
        UserDto user = emailAuthService.getUserByUserId(userId);

        //email 테이블의 email_id(PK) 생성을 위한 uuid 생성
        String uuid = UUID.randomUUID().toString().replace("-", "");

        String certino = emailKeyProvider.createKey();

        //email 테이블에 데이터를 담을 Dto에 uuid와 토큰으로 인증된 user 정보를 입력
        EmailAuthDto emailAuthDto = EmailAuthDto.builder()
                .emailId(uuid)
                .userId(user.getUserId())
                .email(user.getEid())
                .certino(certino)
                .build();
        //유저 정보를 이용하여 인증메일을 보낸다.
        emailAuthService.sendSimpleMessage(emailAuthDto);

        //리턴 값의 body에 이메일 인증 만료 시간을 같이 보낸다.
        Date expireTime = emailAuthService.getExpireTime(emailAuthDto);
        //세계 협정 시간 대신 한국 시간으로 변경 +9
        Calendar cal = Calendar.getInstance();
        cal.setTime(expireTime);
        cal.add(Calendar.HOUR, +9);

        return ResponseEntity.ok().body(cal);
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> emailConfirm(@AuthenticationPrincipal String userId, EmailAuthDto certino) throws Exception {
        //토큰으로 확인한 유저의 uuid와 입력받은 인증번호를 이용해서 DB에 있는지 확인한다.
        //Dto 대신에
        Map<String, String> certinoCheckMap = new HashMap<>();
        certinoCheckMap.put("userId", userId);
        certinoCheckMap.put("certino", certino.getCertino());
        if (emailAuthService.compareCertiNo(certinoCheckMap)) {
            //DB에 있는 것이 확인되면 회원의 상태를 이메일인증(ST00120)으로 변경(update X -> insert 상태 추가)
            emailAuthService.changeUserStatus(userId);
        }
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", "/")
                .body("이메일인증이 완료되었습니다");
    }
}