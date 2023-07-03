package com.jungsuk_2_1.postory.controller;

import com.jungsuk_2_1.postory.dto.*;
import com.jungsuk_2_1.postory.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/profile")
public class ProfileController {
    private ProfileService profileService;

    ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{nic}")
    public ResponseEntity<?> retrieveProfileChannel(@PathVariable(required = true) String nic,
                                                    @AuthenticationPrincipal String userId) {
        try {
            if (profileService.getUserByNickname(nic) == null) {
                throw new RuntimeException("존재하지않는 프로필입니다.");
            }
            //PathVariable로 넘어온 유저의 nic을 받아와서 DB에서 해당하는 유저의 정보 찾기.
            UserDto user = profileService.getUserByNickname(nic);
            //응답 형태 : Map
            Map<String, Object> userChannelMap = new HashMap<>();
            //Profile에 필요한 User 정보만 가져오는 ProfileUserDto에 정보 담기
            ProfileUserDto userInfo = profileService.getProfileUser(nic);

            //user와 channel을 join한 정보와 소유한 channel을 모두 가져오기 위한 List 사용
            List<ProfileChannelDto> channelList = profileService.getProfileChannel(user.getUserId());
            if (channelList.get(0) == null) {
                channelList = new ArrayList<>();
            }

            //가져온 user 정보와 channel 정보를 map에 저장.
            userChannelMap.put("user", userInfo);
            userChannelMap.put("channel", channelList);

            return ResponseEntity.ok().body(userChannelMap);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("errMsg", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @GetMapping("/{nic}/posts")
    public ResponseEntity<?> retrieveProfilePosts(@PathVariable(required = true) String nic,
                                                  @RequestParam(required = false) Integer page,
                                                  @AuthenticationPrincipal String userId) {
        try {
            Map<String, Object> response = getProfileData(nic, page, userId, true);

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("errMsg", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @GetMapping("/{nic}/series")
    public ResponseEntity<?> retrieveProfileSeries(@PathVariable(required = true) String nic,
                                                   @RequestParam(required = false) Integer page,
                                                   @AuthenticationPrincipal String userId) {
        try {
            Map<String, Object> response = getProfileData(nic, page, userId, false);

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("errMsg", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    private Map<String, Object> getProfileData(String nic, Integer page, String userId, boolean isPosts) {
        if (profileService.getUserByNickname(nic) == null) {
            throw new RuntimeException("존재하지않는 프로필입니다.");
        }

        UserDto user = profileService.getUserByNickname(nic);
        ProfileUserDto userInfo = profileService.getProfileUser(nic);

        if (page == null || page == 0) {
            page = 1;
        }

        int offset = (page - 1) * 10;
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("userId", user.getUserId());
        dataMap.put("offset", offset);

        List<?> dataList;
        if (isPosts) {
            dataList = profileService.getProfilePosts(dataMap);
        } else {
            dataList = profileService.getProfileSerise(dataMap);
        }

        //user와 channel을 join한 정보와 소유한 channel을 모두 가져오기 위한 List 사용
        List<ProfileChannelDto> channelList = profileService.getProfileChannel(user.getUserId());
        if (channelList.get(0) == null) {
            channelList = new ArrayList<>();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("user", userInfo);
        response.put(isPosts ? "posts" : "series", dataList);
        response.put("channel",channelList);

        return response;
    }

    @GetMapping
    public ResponseEntity<?> wrongPath() {
        Map<String, String> error = new HashMap<>();
        error.put("errMsg", "잘못된 경로입니다");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @GetMapping("/{nic}/{notExistsPath}")
    public ResponseEntity<?> notFoundPath(@PathVariable(required = true) String notExistsPath,String nic) {
        Map<String, String> error = new HashMap<>();
        if (profileService.getUserByNickname(nic) == null) {
            error.put("errMsg","존재하지않는 프로필입니다");
        }

        if (notExistsPath != "posts" || notExistsPath != "series") {
            error.put("errMsg", "잘못된 경로입니다");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
