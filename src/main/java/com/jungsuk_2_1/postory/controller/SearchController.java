package com.jungsuk_2_1.postory.controller;
import com.jungsuk_2_1.postory.dto.ChannelDto;
import com.jungsuk_2_1.postory.dto.ChannelPostDto;
import com.jungsuk_2_1.postory.dto.SearchChannelDto;
import com.jungsuk_2_1.postory.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/search")
public class SearchController {

    private SearchService searchService;

    @Autowired
    SearchController(SearchService searchService){
        this.searchService = searchService;
    }


    @GetMapping("/channel")
    public ResponseEntity<?> searchChannel(@AuthenticationPrincipal String userId,  @RequestParam(required = false, value = "page", defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "latest") String orderMethod, @RequestParam(required = false, value = "count", defaultValue = "12") int pageSize, @RequestParam(required = false, defaultValue = "all") String option, @RequestParam String keyword) {


        try {
            Map<String, Object> params = new HashMap<>();
            params.put("name", "searchChannel");
            params.put("userId", userId);
            params.put("page", page);
            params.put("orderMethod", orderMethod);
            params.put("pageSize", pageSize);
            params.put("option", option);
            params.put("keyword", keyword);
            params.put("offset", (page - 1) * pageSize);
            List<SearchChannelDto> channels = searchService.searchChannel(params);
            Integer searchCount = channels.size();

            Map<String, Object> data = new HashMap<>();
            data.put("channels", channels);
            data.put("searchCnt", searchCount);

            return ResponseEntity.ok().body(data);
        } catch (Exception e) {
            Map error = new HashMap();
            error.put("errMsg", e.getMessage());

            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/{postType}/posts")
    public ResponseEntity<?> searchPost(@AuthenticationPrincipal String userId, @PathVariable String postType, @RequestParam(required = false, value = "page", defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "latest") String orderMethod, @RequestParam(required = false, value = "count", defaultValue = "12") int pageSize, @RequestParam String option, @RequestParam String keyword){

        System.out.println("postType = " + postType);
        System.out.println("keyword = " + keyword);
        try {
            List<ChannelPostDto> posts = searchService.searchPost(userId,option,keyword, postType ,page, orderMethod,pageSize);


            Integer searchCNt = posts.size();

            System.out.println("post = " + posts);

            Map<String, Object> data = new HashMap<>();
            data.put("posts", posts);
            data.put("searchCnt", searchCNt);

            return ResponseEntity.ok().body(data);
        }catch (Exception e){
            Map error = new HashMap();
            error.put("errMsg",e.getMessage());

            return ResponseEntity.badRequest().body(error);
        }
    }
}
