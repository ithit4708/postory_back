package com.jungsuk_2_1.postory.controller;

import com.jungsuk_2_1.postory.dto.*;
import com.jungsuk_2_1.postory.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/profile/{nic}")
public class ProfileController {
    private ProfileService profileService;

    ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public ResponseEntity<?> retrieveProfileChannel(@PathVariable(required = true) String nic,
                                                    @AuthenticationPrincipal String userId) {
        //PathVariable로 넘어온 유저의 nic을 받아와서 DB에서 해당하는 유저의 정보 찾기.
        UserDto user = profileService.getUserByNickname(nic);
        //응답 형태 : Map
        Map<String, Object> userChannelMap = new HashMap<>();
        //Profile에 필요한 User 정보만 가져오는 ProfileUserDto에 정보 담기
        ProfileUserDto userInfo = profileService.getProfileUser(nic);

        //user와 channel을 join한 정보와 소유한 channel을 모두 가져오기 위한 List 사용
        List<ProfileChannelDto> list = profileService.getProfileChannel(user.getUserId());

        //가져온 user 정보와 channel 정보를 map에 저장.
        userChannelMap.put("user", userInfo);
        userChannelMap.put("channel", list);

        return ResponseEntity.ok().body(userChannelMap);
    }

    @GetMapping("/posts")
    public ResponseEntity<?> retrieveProfilePosts(@PathVariable(required = true) String nic, @RequestParam(required = false) Integer page,
                                                  @AuthenticationPrincipal String userId) {
        try {
            //PathVariable로 넘어온 유저의 nic을 받아와서 DB에서 해당하는 유저의 정보 찾기.
            UserDto user = profileService.getUserByNickname(nic);
            //응답 형태 : Map
            Map<String, Object> userPostsMap = new HashMap<>();
            //Profile에 필요한 User 정보만 가져오는 ProfileUserDto에 정보 담기
            ProfileUserDto userInfo = profileService.getProfileUser(nic);

            if (page == null) {
                page = 1;
            }

            //user와 post를 join한 정보와 소유한 모든 포스트를 가져오기 위한 List 사용
            //포스트는 최신순으로 10개씩 보여지며, 쿼리스트링(?page={})으로 넘어온 값에 따라 최신 10개 단위로 보여준다
            int offset = (page - 1) * 10;
            Map<String,Object> postInfoMap = new HashMap<>();
            postInfoMap.put("userId",user.getUserId());
            postInfoMap.put("offset",offset);
            List<ProfilePostsDto> list = profileService.getProfilePosts(postInfoMap);

            userPostsMap.put("user",userInfo);
            userPostsMap.put("posts",list);

            return ResponseEntity.ok().body(userPostsMap);
        } catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }

    @GetMapping("/series")
    public ResponseEntity<?> retrieveProfileSeries(@PathVariable(required = true) String nic, @RequestParam(required = false) Integer page,
                                                   @AuthenticationPrincipal String userId) {
        try {
            //PathVariable로 넘어온 유저의 nic을 받아와서 DB에서 해당하는 유저의 정보 찾기.
            UserDto user = profileService.getUserByNickname(nic);
            //응답 형태 : Map
            Map<String, Object> userSeriseMap = new HashMap<>();
            //Profile에 필요한 User 정보만 가져오는 ProfileUserDto에 정보 담기
            ProfileUserDto userInfo = profileService.getProfileUser(nic);

            if (page == null) {
                page = 1;
            }
            //user와 post를 join한 정보와 소유한 모든 시리즈를 가져오기 위한 List 사용
            //시리즈는 최신순으로 5개씩 보여지며, 쿼리스트링(?page={})으로 넘어온 값에 따라 최신 5개 단위로 보여준다
            int offset = (page - 1) * 10;
            Map<String,Object> seriesInfoMap = new HashMap<>();
            seriesInfoMap.put("userId",user.getUserId());
            seriesInfoMap.put("offset",offset);
            List<ProfileSeriseDto> list = profileService.getProfileSerise(seriesInfoMap);

            userSeriseMap.put("user",userInfo);
            userSeriseMap.put("serise",list);

            return ResponseEntity.ok().body(userSeriseMap);
        } catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }

}
