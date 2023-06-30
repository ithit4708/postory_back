package com.jungsuk_2_1.postory.controller;

import com.jungsuk_2_1.postory.dto.EmailAuthDto;
import com.jungsuk_2_1.postory.dto.UserDto;
import com.jungsuk_2_1.postory.service.EmailAuthService;
import com.jungsuk_2_1.postory.service.EmailKeyProvider;
import com.jungsuk_2_1.postory.service.UserService;
import com.jungsuk_2_1.postory.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/email")
public class EmailAuthController {
    private EmailAuthService emailAuthService;
    private EmailKeyProvider emailKeyProvider;
    private UserService userService;

    EmailAuthController(EmailAuthService emailAuthService, EmailKeyProvider emailKeyProvider) {
        this.emailAuthService = emailAuthService;
        this.emailKeyProvider = emailKeyProvider;
    }

    @PostMapping("/auth")
    public ResponseEntity<?> emailAuth(@AuthenticationPrincipal String userId, @RequestBody UserDto requsetUser) throws Exception {
        try {
            // 토큰으로 확인된 로그인한 사용자 정보
            UserDto loginUser = emailAuthService.getUserByUserId(userId);
            // 인증번호 난수 생성
            String certino = emailKeyProvider.createKey();
            // 이메일 주소 변수 초기화
            String email;
            // 회원가입한 유저의 eid와 입력받은 eid가 같은지 확인
            if (Objects.equals(loginUser.getEid(), requsetUser.getEid())) {
                // 같은 경우, 로그인한 사용자의 이메일 주소 사용
                email = loginUser.getEid();
            } else {
                // 다른 경우, 입력받은 이메일 주소로 중복 체크
                if (emailAuthService.CheckExistsEid(requsetUser.getEid())) {
                    throw new RuntimeException("이미 존재하는 이메일입니다");
                }
                // 중복 체크 통과되면 입력받은 이메일 주소 사용
                email = requsetUser.getEid();
            }

            // emailAuthDto 생성
            EmailAuthDto emailAuthDto = EmailAuthDto.builder()
                    .userId(loginUser.getUserId())
                    .email(email)
                    .certino(certino)
                    .build();

            // 인증메일 보내기 및 email 테이블 업데이트
            emailAuthService.sendSimpleMessage(emailAuthDto);

            // 이메일 인증 만료 시간 계산
            Date expDate = emailAuthService.getExpireTime(emailAuthDto);

            Date newDate = DateUtils.addHours(expDate, 9); // +9시간을 적용한 새로운 Date 객체

            Map<String, Date> map = new HashMap<>();
            map.put("expDate", newDate);

            return ResponseEntity.ok().body(map);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("errMsg", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> emailConfirm(@AuthenticationPrincipal String userId, @RequestBody EmailAuthDto requsetUser) throws Exception {
        //토큰으로 확인한 유저의 uuid와 입력받은 인증번호를 이용해서 DB에 있는지 확인한다.
        //Dto 대신에
        try {
            // 토큰으로 확인된 로그인한 사용자 정보
            UserDto loginUser = emailAuthService.getUserByUserId(userId);

            Map<String, String> certinoCheckMap = new HashMap<>();
            certinoCheckMap.put("userId", loginUser.getUserId());
            certinoCheckMap.put("certino", requsetUser.getCertino());

            //로그인 유저의 uuid와 입력받은 인증번호로 email 테이블에 정보가 있는지 확인!
            if (emailAuthService.compareCertiNo(certinoCheckMap)) {
                String email;
                // 회원가입한 유저의 eid와 입력받은 eid가 같은지 확인
                if (Objects.equals(loginUser.getEid(), requsetUser.getEmail())) {
                    // 같은 경우, 로그인한 사용자의 이메일 주소 사용
                    email = loginUser.getEid();
                } else {
                    // 다른 경우, 입력받은 이메일 주소 사용
                    email = requsetUser.getEmail();
                }

                // emailAuthDto 생성
                EmailAuthDto userInfo = EmailAuthDto.builder()
                        .userId(loginUser.getUserId())
                        .email(email)
                        .build();
                //다른 경우, 유저의 이메일 변경
                if (Objects.equals(userInfo.getEmail(), requsetUser.getEmail())) {
                    emailAuthService.changeUserEmail(userInfo);
                }
                //같은 경우 & 다른 경우 이메일 변경 후
                emailAuthService.changeUserStatus(userInfo);
            } else {
                throw new RuntimeException("인증에 실패했습니다.인증번호를 확인해주세요");
            }
            Map<String,String> okMsg = new HashMap<>();
            okMsg.put("okMsg","이메일 인증에 성공하였습니다");

            return ResponseEntity.ok().body(okMsg);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String,String> errMsg = new HashMap<>();
            errMsg.put("errMsg",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errMsg);
        }
    }
}