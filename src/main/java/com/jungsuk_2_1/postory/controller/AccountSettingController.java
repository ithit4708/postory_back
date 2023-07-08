package com.jungsuk_2_1.postory.controller;

import com.jungsuk_2_1.postory.dto.ProfileUserDto;
import com.jungsuk_2_1.postory.dto.UserDto;
import com.jungsuk_2_1.postory.service.AccountSettingService;
import com.jungsuk_2_1.postory.service.EmailAuthService;
import com.jungsuk_2_1.postory.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/account/settings")
public class AccountSettingController {
    AccountSettingService accountSettingService;
    UserService userService;
    EmailAuthService emailAuthService;

    //비밀번호 암호화
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AccountSettingController(AccountSettingService accountSettingService, UserService userService,
                                    EmailAuthService emailAuthService) {
        this.accountSettingService = accountSettingService;
        this.userService = userService;
        this.emailAuthService = emailAuthService;
    }

    @GetMapping("/profile")
    public ResponseEntity<?> retrieveProfile(@AuthenticationPrincipal String userId) {
        try {
            ProfileUserDto profileUserDto = accountSettingService.findUserByUserId(userId);

            return ResponseEntity.ok().body(profileUserDto);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("errMsg", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PutMapping("/profile")
    public ResponseEntity<Object> uploadFile(@AuthenticationPrincipal String userId,
                                             @RequestParam("file") MultipartFile userImgFile,
                                             @RequestParam("nic") String nic,
                                             @RequestParam("userIntro") String userIntro) {
        try {
            if (Objects.equals(nic, "")) {
                throw new RuntimeException("닉네임을 입력해주세요");
            }

            String originalFilename = userImgFile.getOriginalFilename();
            log.warn("originalFilename = {}", originalFilename);
            String fileExtension = StringUtils.getFilenameExtension(originalFilename);
            ProfileUserDto profileUserDto = accountSettingService.findUserByUserId(userId);

            if (fileExtension == null) {
                //userImgFile == null이면, 기본이미지(null)인데 변경을 안하거나, 있던 이미지를 삭제하거나
                //두 개의 경우의 수를 충족시키기 위해 DB에 null을 저장
                UserDto notExistUploadUserImg = UserDto.builder()
                        .userImgPath(null)
                        .nic(nic)
                        .userIntro(userIntro)
                        .userId(userId)
                        .build();
                accountSettingService.changeUserProfile(notExistUploadUserImg);

                File userfile = new File("src/main/resources/" + profileUserDto.getUserImgPath());
                if (userfile.exists()) {
                    if (userfile.delete()) {
                        System.out.println("파일삭제 성공");
                    } else {
                        System.out.println("파일삭제 실패");
                    }
                } else {
                    System.out.println("파일이 존재하지 않습니다.");
                }
                return ResponseEntity.ok().body(null);
            }

            // 유저 이미지가 존재한다는건, 기존의 이미지가 있거나, 새로운 파일을 업로드 했거나
            // 업로드된 이미지를 먼저 정적 이미지 폴더에 저장
            String saveFileName = accountSettingService.saveImage(userImgFile, userId);
            //폴더에 저장된 파일에 경로를 앞에 붙여서 DB에 넣을 준비
            saveFileName = "/static/img/user/" + saveFileName;
            //DB에 update 하기 위한 userDto 초기화
            UserDto userDto = UserDto.builder()
                    .userImgPath(saveFileName)
                    .nic(nic)
                    .userIntro(userIntro)
                    .userId(userId)
                    .build();
            //user 테이블 UPDATE 실시
            accountSettingService.changeUserProfile(userDto);

            return ResponseEntity.ok().body(null);

        } catch (IOException e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("errMsg", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("errMsg", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PutMapping("/password")
    public ResponseEntity<?> updatePassword(@AuthenticationPrincipal String userId,
                                            @RequestBody UserDto userDto) {
        //로그인 되어 있는 상태에서 비밀번호 재설정을 누른 상태이므로 토큰에 유저 아이디가 있음(인증된 상태라는 것)
        //현재 비밀번호가 들어오고, 새로바꿀 비밀번호가 들어옴 (매핑되는 변수 2개, currenrPwd & newPwd )

        try {
            //1. 현재 비밀번호가 맞는 확인하는 절차. ( Boolean )
            //1.1 암호화된 비밀번호를 디코딩해서 확인해야함 ( UserController 참고하기 )
            if (Objects.equals(userDto.getCurrentPwd(), "")) {
                throw new RuntimeException("현재 비밀번호를 입력해주세요");
            }
            if (Objects.equals(userDto.getNewPwd(), "")) {
                throw new RuntimeException("새로운 비밀번호를 입력해주세요");
            }
            //암호화된 비밀번호를 맡는지 확인하기 위해 UserService에서 로그인 때 만들었던 getByCredentials() 메서드를 사용
            //사용하기위해서는 userEid가 필요함
            UserDto userEid = emailAuthService.getUserByUserId(userId);
            //getByCredentials() 메서드를 사용해서 비밀번호 검증.
            UserDto originalUser = userService.getByCredentials(
                    userEid.getEid(),
                    userDto.getCurrentPwd(),
                    passwordEncoder);
            //현재 비밀번호와 새로 입력한 비밀번호가 같으면 예외 던지기.
            if (originalUser != null && passwordEncoder.matches(userDto.getNewPwd(), originalUser.getPwd())) {
                throw new IllegalStateException("현재 사용 중인 비밀번호와 동일합니다.");
            }
            //비밀번호가 일치하면 originalUser != null
            if (originalUser != null) {
                //2. 1번이 통과되면 새로운 비밀번호를 암호화
                String newPwd = passwordEncoder.encode(userDto.getNewPwd()); //
                //해서 DB에 등록 UPDATE ( HttpStatus.OK )
                Map<String, String> userPwdMap = new HashMap<>();
                userPwdMap.put("userId", userId);
                userPwdMap.put("newPwd", newPwd);
                accountSettingService.changePwd(userPwdMap);
                //비밀번호 변경 완료
                return ResponseEntity.ok().body("비밀번호가 변경되었습니다");
            } else {
                //현재 비밀번호가 일치하지 않으면 originalUser == null, getByCredentials의 결과가 null
                throw new NullPointerException("현재 비밀번호가 일치하지 않습니다.");
            }

        } catch (IllegalStateException i) {
            //현재 비밀번호가 새로운 비밀번호가 중복 입력이면 409 상태코드 반환
            i.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("errMsg", i.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        } catch (NullPointerException n) {
            //입력한 현재 비밀번호가 DB의 비밀번호와 일치하지 않으면 401 반환
            n.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("errMsg", n.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("errMsg", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}
