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

        String UPLOAD_PATH = "";
        log.warn("UPLOAD_PATH = {}", UPLOAD_PATH);

        try {
            if (Objects.equals(nic, "")) {
                throw new RuntimeException("닉네임을 입력해주세요");
            }
            if (Objects.equals(userImgFile, null)) {
                //유저 이미지 경로가 빈문자열로 오면 null로 변환해서 DB에 저장
                UserDto userDto = UserDto.builder()
                        .userImgPath(null)
                        .nic(nic)
                        .userIntro(userIntro)
                        .build();
                accountSettingService.changeUserProfile(userDto);
            }

//            if (userImgFile != null) {
//                String fileId = userId;// 현재 날짜와 랜덤 정수값으로 새로운 파일명 만들기
//                String originName = userImgFile.getOriginalFilename(); // ex) 파일.jpg
//                String fileExtension = originName.substring(originName.lastIndexOf(".") + 1); // ex) jpg
//                originName = originName.substring(0, originName.lastIndexOf(".")); // ex) 파일
//
//                File fileSave = new File(UPLOAD_PATH, fileId + "." + fileExtension); // ex) fileId.jpg
//                if (!fileSave.exists()) { // 폴더가 없을 경우 폴더 만들기
//                    fileSave.mkdirs();
//                }
//
//                userImgFile.transferTo(fileSave); // fileSave의 형태로 파일 저장
//            }

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
        return new ResponseEntity<Object>("Success", HttpStatus.OK);
    }

}
