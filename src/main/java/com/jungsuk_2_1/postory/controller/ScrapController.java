package com.jungsuk_2_1.postory.controller;

import com.jungsuk_2_1.postory.dto.PostDto;
import com.jungsuk_2_1.postory.service.ScrapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/scrap")
public class ScrapController {
    ScrapService scrapService;
    ScrapController(ScrapService scrapService){
        this.scrapService = scrapService;
    }
    @PostMapping
    public ResponseEntity<?> doScrap(@AuthenticationPrincipal String userId, @RequestBody PostDto postId) {
        Map<String, Object> scrapInfoMap = new HashMap<>();
        scrapInfoMap.put("userId",userId);
        scrapInfoMap.put("postId",postId.getPostId());
        log.warn("scrapInfoMap = {}", scrapInfoMap);

        scrapService.addToScrapList(scrapInfoMap);

        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping("/cancle")
    public ResponseEntity<?> cancleScrap(@AuthenticationPrincipal String userId, @RequestParam Integer postId) {
        Map<String, Object> scrapCancleInfoMap = new HashMap<>();
        scrapCancleInfoMap.put("userId",userId);
        scrapCancleInfoMap.put("postId",postId);
        log.warn("scrapCancleInfoMap = {}", scrapCancleInfoMap);

        scrapService.removeFromScrapList(scrapCancleInfoMap);

        return ResponseEntity.ok().body(null);
    }

//    @GetMapping
//    public ResponseEntity<?> retrieveScrapPostList(@AuthenticationPrincipal String userId) {
//
//    }
}
