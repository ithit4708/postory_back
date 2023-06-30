package com.jungsuk_2_1.postory.controller;

import com.jungsuk_2_1.postory.dto.PostDto;
import com.jungsuk_2_1.postory.dto.StudioPostDto;
import com.jungsuk_2_1.postory.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("post")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }
    @PostMapping("/create")
    ResponseEntity<?> createPost(@AuthenticationPrincipal String userId, @RequestBody PostDto postDto) {
        try {
            StudioPostDto studioPostDto = postService.createPost(userId, postDto);

            Map<String,Object> data = new HashMap<>();

            if (studioPostDto == null) {
                return ResponseEntity.notFound().build(); // StudioPostDto가 null인 경우 404 응답
            }

            data.put("post",studioPostDto);

            return ResponseEntity.ok().body(data);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("errMsg", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/{postId}/edit")
    ResponseEntity<?> updatePost(@AuthenticationPrincipal String userId,@PathVariable Integer postId ,@RequestBody PostDto postDto){
        try {
            StudioPostDto studioPostDto = postService.updatePost(userId, postId ,postDto);

            Map<String,Object> data = new HashMap<>();

            if (studioPostDto == null) {
                return ResponseEntity.notFound().build(); // StudioPostDto가 null인 경우 404 응답
            }

            data.put("post",studioPostDto);

            return ResponseEntity.ok().body(data);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("errMsg", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}