package com.jungsuk_2_1.postory.controller;

import com.jungsuk_2_1.postory.dto.ProfileUserDto;
import com.jungsuk_2_1.postory.dto.UserDto;
import com.jungsuk_2_1.postory.service.AccountSettingService;
import com.jungsuk_2_1.postory.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/account/settings")
public class AccountSettingController {
    AccountSettingService accountSettingService;
    ResourceLoader resourceLoader;

    public AccountSettingController(AccountSettingService accountSettingService, ResourceLoader resourceLoader) {
        this.accountSettingService = accountSettingService;
        this.resourceLoader = resourceLoader;
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
            if (Objects.equals(userImgFile, null)) {
                //유저 이미지 경로가 빈문자열로 오면 null로 변환해서 DB에 user 테이블 UPDATE 실시
                UserDto userDto = UserDto.builder()
                        .userImgPath(null)
                        .nic(nic)
                        .userIntro(userIntro)
                        .userId(userId)
                        .build();
                accountSettingService.changeUserProfile(userDto);
            }

            //유저 이미지가 존재하면 일단 먼저 정적 이미지 폴더에 저장
            String saveFileName = accountSettingService.saveImage(userImgFile, userId);
            //폴더에 저장된 파일에 경로를 앞에 붙여서 DB에 넣을 준비
            saveFileName = "static/img/user/" + saveFileName;
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

}
