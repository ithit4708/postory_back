package com.jungsuk_2_1.postory.controller;

import com.jungsuk_2_1.postory.dto.PostDto;
import com.jungsuk_2_1.postory.dto.SubscriptionPostDto;
import com.jungsuk_2_1.postory.service.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/library/like")
public class LikeController {
    LikeService likeService;

    LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping
    public ResponseEntity<?> doLike(@AuthenticationPrincipal String userId, @RequestBody PostDto postId) {
        Map<String, Object> likeInfoMap = new HashMap<>();
        likeInfoMap.put("userId",userId);
        likeInfoMap.put("postId",postId.getPostId());

        likeService.addToLikeList(likeInfoMap);

        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping("/cancle")
    public ResponseEntity<?> cancleLike(@AuthenticationPrincipal String userId, @RequestParam Integer postId) {
        Map<String, Object> likeCancleInfoMap = new HashMap<>();
        likeCancleInfoMap.put("userId", userId);
        likeCancleInfoMap.put("postId", postId);

        likeService.removeFromLikeList(likeCancleInfoMap);

        return ResponseEntity.ok().body(null);
    }

    @GetMapping
    public ResponseEntity<?> retrieveLikePostList(@AuthenticationPrincipal String userId) {
        List<SubscriptionPostDto> likePostList = likeService.getLikePostList(userId);

        return ResponseEntity.ok().body(likePostList);
    }
}
