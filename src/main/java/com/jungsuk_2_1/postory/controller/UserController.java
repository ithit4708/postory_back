package com.jungsuk_2_1.postory.controller;

import com.jungsuk_2_1.postory.dto.ResponseDto;
import com.jungsuk_2_1.postory.security.TokenProvider;
import com.jungsuk_2_1.postory.dto.UserDto;
import com.jungsuk_2_1.postory.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {
    private UserService userService;
    private TokenProvider tokenProvider;

    UserController(UserService userService, TokenProvider tokenProvider) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        try {
            if (userDto == null || userDto.getPwd() == null) {
                throw new RuntimeException("Invalid Password value");
            }
            //uuid 생성
            String uuid = UUID.randomUUID().toString().replace("-", "");

            //요청을 이용해 저장할 유저 만들기
            UserDto user = UserDto.builder()
                    .eid(userDto.getEid())
                    .pwd(passwordEncoder.encode(userDto.getPwd())) //회원가입시 패스워드를 인코딩해 저장하는 부분!
                    .userId(uuid) //회원가입시 uuid를 발급해서 저장하는 부분!
                    .nic(userDto.getNic())
                    .msgAlowYn(true)
                    .build();

//            //서비스를 이용해 리포지터리에 유저 저장
//            UserDto registerdUser = userService.create(user);
//
//            //유저 정보는 항상 하나이므로 리스트로 만들어야 하는 ResponseDTO를 사용하지 않고 그냥 UserDTO리턴.
//            UserDto responseUserDto = UserDto.builder()
//                    .userId(registerdUser.getUserId())
//                    .eid(registerdUser.getEid())
//                    .pwd(registerdUser.getPwd())
//                    .nic(registerdUser.getNic())
//                    .signupDtm(registerdUser.getSignupDtm())
//                    .build();
//            return ResponseEntity.ok().body(responseUserDto);
            userService.create(user);
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            ResponseDto responseDTO = ResponseDto.builder().error(e.getMessage()).build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDto userDto) {
        UserDto user = userService.getByCredentials(
                userDto.getEid(),
                userDto.getPwd(),
                passwordEncoder); //로그인시 서비스에 BCryptPasswordEncoder 객체를 넘겨주기

//        if (user != null) {
//            final UserDTO responseDTO = UserDTO.builder()
//                    .username(user.getUsername())
//                    .id(user.getId())
//                    .build();
//            return ResponseEntity.ok().body(responseDTO);
        if (user != null) {
            //토큰 생성
            final String token = tokenProvider.create(user);
            final UserDto responseRequestUserDto = userDto.builder()
                    .eid(user.getEid())
                    .userId(user.getUserId())
                    .pwd(user.getPwd())
                    .nic(user.getNic())
                    .signupDtm(user.getSignupDtm())
                    .token(token)
                    .build();
            return ResponseEntity.ok().body(responseRequestUserDto);
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