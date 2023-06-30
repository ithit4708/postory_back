package com.jungsuk_2_1.postory.controller;

import com.jungsuk_2_1.postory.dto.SubscriptionDto;
import com.jungsuk_2_1.postory.service.SubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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

//    @GetMapping
//
//    @GetMapping("channel")
}
