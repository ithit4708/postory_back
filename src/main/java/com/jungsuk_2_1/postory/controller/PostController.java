package com.jungsuk_2_1.postory.controller;

import com.jungsuk_2_1.postory.dto.PostDto;
import com.jungsuk_2_1.postory.dto.StudioPostDto;
import com.jungsuk_2_1.postory.service.PostService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    ResponseEntity<?> createPost(@AuthenticationPrincipal String userId, @RequestBody PostDto postDto){

        try {
            StudioPostDto studioPostDto = postService.createPost(userId,postDto);

            Map<String, StudioPostDto> data = new HashMap();
            data.put("data",studioPostDto);


            return ResponseEntity.ok().body(data);
        }catch (Exception e){
            Map error = new HashMap();
            error.put("errMsg",e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }




}
