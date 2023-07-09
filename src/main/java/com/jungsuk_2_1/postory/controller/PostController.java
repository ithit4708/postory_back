package com.jungsuk_2_1.postory.controller;

import com.jungsuk_2_1.postory.dto.*;
import com.jungsuk_2_1.postory.service.PostService;
import com.jungsuk_2_1.postory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;


    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }


    @PostMapping("/{postId}/edit")
    ResponseEntity<?> updatePost(@AuthenticationPrincipal String userId, @PathVariable Integer postId, @RequestBody PostDto postDto) {
        try {
            PostDto post = postService.updatePost(userId, postId, postDto);

            Map<String, Object> data = new HashMap<>();

            if (post == null) {
                return ResponseEntity.notFound().build(); // PostDto가 null인 경우 404 응답
            }

            data.put("post", post);

            return ResponseEntity.ok().body(data);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("errMsg", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Integer postId) {
        try {
            boolean doesExist = postService.deletePost(postId);

            Map<String, Object> data = new HashMap<>();

            if (doesExist == false) {
                data.put("doesDeleted?", "Success to delete");
            } else {
                data.put("doesDeleted?", "Failed to delete");
            }

            return ResponseEntity.ok().body(data);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("errMsg", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/create")
    ResponseEntity<?> createPost(@AuthenticationPrincipal String userId, @RequestBody PostDto postDto) {
        try {
            PostDto post = postService.createPost(userId, postDto);

            Map<String, Object> data = new HashMap<>();

            if (post == null) {
                return ResponseEntity.notFound().build(); // PostDto가 null인 경우 404 응답
            }

            data.put("post", post);

            return ResponseEntity.ok().body(data);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("errMsg", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> retrievePost(@AuthenticationPrincipal String userId, @PathVariable Integer postId) {

        try {
            ChannelUserDto writer = postService.getUserByPostId(postId);
            PostViewDto post = postService.readPostById(userId, postId);
            ChannelSimpleDto channel = postService.getChannelByPostId(postId);
            Map<String, Object> data = new HashMap<>();
            data.put("post", post);
            data.put("writer", writer);
            data.put("channel", channel);

            return ResponseEntity.ok().body(data);
        } catch (Exception e) {
            Map error = new HashMap();
            error.put("errMsg", e.getMessage());

            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/{postId}/like/{nic}")
    public ResponseEntity<?> retrieveLike(@PathVariable Integer postId, @PathVariable String nic){

        try{
            boolean isLiked = postService.checkLike(postId, nic);

            Map<String, Object>  data = new HashMap<>();
            data.put("data", isLiked);

            return ResponseEntity.ok().body(data);

        } catch (Exception e) {
            Map error = new HashMap();
            error.put("errMsg",e.getMessage());

            return ResponseEntity.badRequest().body(error);
        }
    }
}