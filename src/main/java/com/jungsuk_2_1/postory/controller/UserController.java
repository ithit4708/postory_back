package com.jungsuk_2_1.postory.controller;

import com.jungsuk_2_1.postory.dto.HeaderChannelDto;
import com.jungsuk_2_1.postory.dto.HeaderUserDto;
import com.jungsuk_2_1.postory.security.TokenProvider;
import com.jungsuk_2_1.postory.dto.UserDto;
import com.jungsuk_2_1.postory.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
            if (Objects.equals(userDto.getEid(), "")) {
                throw new RuntimeException("Enter EMAIL");
            }
            if (Objects.equals(userDto.getPwd(), "")) {
                throw new RuntimeException("Enter PWD");
            }
            if (Objects.equals(userDto.getNic(), "")) {
                throw new RuntimeException("Enter NICKNAME");
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

            //DB에 유저를 저장(생성)하는 create()메서드 호출(반환X, void), 유저 상태도 "신규"로 생성
            userService.create(user);
            log.info("Signup Success");
            //회원가입 OK
            return ResponseEntity.ok().body(null);

        } catch (Exception e) {
            e.printStackTrace();
            Map error = new HashMap();
            error.put("errMsg",e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDto userDto) throws Exception {
        //이미 로그인이 되어 있는 상태라면 다시 이전페이지로 리다이렉트 보내기

        //[유효성검사] 입력받은 이메일로 유저정보를 찾고, 그 유저정보에서 입력받은 비밀번호와 일치여부 확인.
        //확인된 유저를 객체로 반환. 일치안하면 null 반환
        UserDto user = userService.getByCredentials(
                userDto.getEid(),
                userDto.getPwd(),
                passwordEncoder); //서비스에 BCryptPasswordEncoder 객체를 넘겨주기

        try {
            if (user != null) {
                //로그인에
                //유저 개인의 JWT 토큰 생성
                final String token = tokenProvider.create(user);
                //로그인한 유저 상태 확인
                String userStatus = userService.checkUserStatus(user);
                //유저의 상태가 신규=ST00110
                if (Objects.equals(userStatus, "ST00110")) {
                    HeaderUserDto newUser = HeaderUserDto.builder()
                            .token(token)
                            .status("ST00110")
                            .build();
                    return ResponseEntity.ok().body(newUser);
                }
                //유저의 상태가 신규가 아님 =(ST00120) - 이메일인증 완료된 회원
                if (Objects.equals(userStatus, "ST00120")) {
                    //Map 형태로 반환하기 위해 HashMap생성
                    Map<String,Object> headerMap = new HashMap<>();

                    //user와 user_status를 join한 정보를 가져오기위한 HeaderUserDto 사용
                    HeaderUserDto userInfo = userService.getHeaderUserInfo(user.getUserId());
                    HeaderUserDto headerUserInfo = HeaderUserDto.builder()
                            .token(token)
                            .status(userInfo.getStatus())
                            .userImgPath(userInfo.getUserImgPath())
                            .nic(userInfo.getNic()).build();
                    //user와 channel을 join한 정보와 소유한 channel을 모두 가져오기 위한 List 사용
                    List<HeaderChannelDto> list = userService.getHeaderInfo(user.getUserId());

                    //가져온 user 정보와 channel 정보를 map에 저장.
                    headerMap.put("user",headerUserInfo);
                    headerMap.put("channel",list);

                    return ResponseEntity.ok().body(headerMap);
                }
            }
            throw new RuntimeException("Login Failed");
        } catch (Exception e) {
            e.printStackTrace();
            Map error = new HashMap();
            error.put("errMsg",e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
    }
}