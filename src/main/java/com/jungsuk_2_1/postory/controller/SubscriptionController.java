package com.jungsuk_2_1.postory.controller;

import com.jungsuk_2_1.postory.dto.ProfileUserDto;
import com.jungsuk_2_1.postory.dto.SubscriptionDto;
import com.jungsuk_2_1.postory.dto.UserDto;
import com.jungsuk_2_1.postory.service.SubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {
    SubscriptionService subscriptionService;
    @Autowired
    SubscriptionController(SubscriptionService subscriptionService){
        this.subscriptionService = subscriptionService;
    }

    @PostMapping
    public ResponseEntity<?> doSubscription(@AuthenticationPrincipal String userId, @RequestBody SubscriptionDto chnlId) {
        Map<String, Object> subInfoMap = new HashMap<>();
        subInfoMap.put("userId",userId);
        subInfoMap.put("chnlId",chnlId.getChnlId());
        log.warn("subInfoMap = {}",subInfoMap);

        subscriptionService.addToSubscriptionList(subInfoMap);

        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping("/cancle")
    public ResponseEntity<?> cancleSubscription(@AuthenticationPrincipal String userId, @RequestBody SubscriptionDto chnlId){
        Map<String, Object> subCancleInfoMap = new HashMap<>();
        subCancleInfoMap.put("userId",userId);
        subCancleInfoMap.put("chnlId",chnlId.getChnlId());
        log.warn("subCancleInfoMap = {}",subCancleInfoMap);

        subscriptionService.removeFromSubscriptionList(subCancleInfoMap);

        return ResponseEntity.ok().body(null);
    }

//    @GetMapping
//    public ResponseEntity<?> retrieveSubscriptionList(@AuthenticationPrincipal String userId) {
//
//    }
//    @GetMapping("channel")
//private Map<String, Object> getSubscriptionData(Integer page, String userId, boolean isPosts) {
//    if (profileService.getUserByNickname(nic) == null) {
//        throw new RuntimeException("존재하지않는 프로필입니다.");
//    }
//
//    UserDto user = profileService.getUserByNickname(nic);
//    ProfileUserDto userInfo = profileService.getProfileUser(nic);
//
//    if (page == null || page == 0) {
//        page = 1;
//    }
//
//    int offset = (page - 1) * 10;
//    Map<String, Object> dataMap = new HashMap<>();
//    dataMap.put("userId", user.getUserId());
//    dataMap.put("offset", offset);
//
//    List<?> dataList;
//    if (isPosts) {
//        dataList = profileService.getProfilePosts(dataMap);
//    } else {
//        dataList = profileService.getProfileSerise(dataMap);
//    }
//
//    Map<String, Object> response = new HashMap<>();
//    response.put("user", userInfo);
//    response.put(isPosts ? "posts" : "series", dataList);
//
//    return response;
//}
}
