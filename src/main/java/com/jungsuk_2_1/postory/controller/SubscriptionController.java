package com.jungsuk_2_1.postory.controller;

import com.jungsuk_2_1.postory.dto.ChannelDto;
import com.jungsuk_2_1.postory.dto.SubscriptionChannelDto;
import com.jungsuk_2_1.postory.dto.SubscriptionPostDto;
import com.jungsuk_2_1.postory.service.SubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {
    SubscriptionService subscriptionService;

    @Autowired
    SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping
    public ResponseEntity<?> doSubscription(@AuthenticationPrincipal String userId, @RequestBody ChannelDto chnlId) {
        Map<String, Object> subInfoMap = new HashMap<>();
        subInfoMap.put("userId", userId);
        subInfoMap.put("chnlId", chnlId.getChnlId());
        log.warn("subInfoMap = {}", subInfoMap);

        subscriptionService.addToSubscriptionList(subInfoMap);

        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping("/cancle")
    public ResponseEntity<?> cancleSubscription(@AuthenticationPrincipal String userId, @RequestParam Integer chnlId) {
        Map<String, Object> subCancleInfoMap = new HashMap<>();
        subCancleInfoMap.put("userId", userId);
        subCancleInfoMap.put("chnlId", chnlId);

        subscriptionService.removeFromSubscriptionList(subCancleInfoMap);

        return ResponseEntity.ok().body(null);
    }

    @GetMapping
    public ResponseEntity<?> retrieveSubscriptionPostList(@AuthenticationPrincipal String userId) {
        //로그인한 유저의 uuid로 유저가 구독한 채널의 모든 포스트 목록 List 불러오기
        List<SubscriptionPostDto> subPostList = subscriptionService.getSubscriptionPostList(userId);

        return ResponseEntity.ok().body(subPostList);
    }

    @GetMapping("channel")
    public ResponseEntity<?> retrieveSubscriptionChannelList(@AuthenticationPrincipal String userId) {
        //로그인한 유저의 uuid로 유저가 구독한 모든 채널의 목록 불러오기
        List<SubscriptionChannelDto> subChannelList = subscriptionService.getSubscriptionChannelList(userId);

        return ResponseEntity.ok().body(subChannelList);
    }
}
