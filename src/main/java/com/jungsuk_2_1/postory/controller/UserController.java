package com.jungsuk_2_1.postory.controller;

import com.jungsuk_2_1.postory.dto.ResponseDto;
import com.jungsuk_2_1.postory.security.TokenProvider;
import com.jungsuk_2_1.postory.dto.UserDto;
import com.jungsuk_2_1.postory.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {
    private UserService userService;
    private TokenProvider tokenProvider;

    //생성자로 객체 주입받는 방법(Autowired 생략 가능)
    UserController(UserService userService, TokenProvider tokenProvider) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    //비밀번호 암호화
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        try {
            //요청받은 객체 유효성 검사
            if (userDto == null) {
                throw new RuntimeException("Invalid value");
            }
            if (userDto.getPwd() == null) {
                throw new RuntimeException("Invalid Password value");
            }
            if (userDto.getEid() == null) {
                throw new RuntimeException("Invalid EmailId value");
            }
            if (userDto.getNic() == null) {
                throw new RuntimeException("Invalid NicName value");
            }
            //uuid 생성
            String uuid = UUID.randomUUID().toString().replace("-", "");

            //요청에 들어온 정보와 추가할 정보를 더해서 DB에 저장할 유저정보 만들기
            UserDto user = UserDto.builder()
                    .eid(userDto.getEid()) //입력받은 이메일 아이디
                    .pwd(passwordEncoder.encode(userDto.getPwd())) //회원가입시 패스워드를 인코딩해 저장하는 부분!
                    .userId(uuid) //회원가입시 uuid를 발급해서 저장하는 부분!
                    .nic(userDto.getNic()) //입력받은 닉네임
                    .msgAlowYn(true) //메시지 허용여부의 Default를 true로 설정
                    .build();

            //DB에 유저를 저장(생성)하는 create()메서드 호출(반환X, void)
            userService.create(user);
            log.info("Signup Success");
            //회원가입 후 로그인페이지로 리다이렉트(302) 요청 - HttpStatus.FOUND
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("Location", "/login")
                    .body(null);
        } catch (Exception e) {
            ResponseDto responseDTO = ResponseDto.builder().error(e.getMessage()).build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDto userDto) {
        //[유효성검사] 입력받은 이메일로 유저정보를 찾고, 그 유저정보에서 입력받은 비밀번호와 일치여부 확인.
        //확인된 유저를 객체로 반환. 일치안하면 null 반환
        UserDto user = userService.getByCredentials(
                userDto.getEid(),
                userDto.getPwd(),
                passwordEncoder); //서비스에 BCryptPasswordEncoder 객체를 넘겨주기
        if (user != null) {
            //로그인에
            //유저 개인의 JWT 토큰 생성
            final String token = tokenProvider.create(user);
            //로그인한 유저 상태 확인
            String userStatus = userService.checkUserStatus(user);
            //유저의 상태가 신규=ST00110 이면 이메일 인증 페이지로 가라고 리다이렉트 요청 넘겨주기
            if (Objects.equals(userStatus, "ST00110")) {
                return ResponseEntity.status(HttpStatus.FOUND)
                        .header("Location", "/auth/email")
                        .body("회원상태 : 신규 / 이메일인증 페이지로 리다이렉트 합니다.");
            }
            //유저의 상태가 신규가 아니면 body에 토큰 싫어서 보내기
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("Location", userDto.getPrevUrl())
                    .body(token);
        } else {
            ResponseDto responseDTO = ResponseDto.builder()
                    .error("Login failed")
                    .build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }
    }
}