package com.jungsuk_2_1.postory.controller;

import com.jungsuk_2_1.postory.dto.*;
import com.jungsuk_2_1.postory.service.ChannelService;
import com.jungsuk_2_1.postory.service.PostService;
import com.jungsuk_2_1.postory.service.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/channel")
public class ChannelController{

    private final ChannelService channelService;
    private final PostService postService;
    private final SeriesService seriesService;

    @Autowired
    ChannelController(ChannelService channelService, PostService postService, SeriesService seriesService){
        this.channelService = channelService;
        this.postService = postService;
        this.seriesService = seriesService;
    }

    @GetMapping("/{chnlUri}")
    public ResponseEntity<?> retrieveChannel(@PathVariable(required = false) String chnlUri){

        try {
            ChannelDto channel = channelService.retrieve(chnlUri);
            ChannelUserDto user = channelService.getUserByChannelUri(chnlUri);
            List<ChannelPostDto> webtoons = postService.getPostsByChnlUri(chnlUri,"webtoon", 1, "latest",6);
            List<ChannelPostDto> webnovels = postService.getPostsByChnlUri(chnlUri,"webnovel", 1, "latest",6);
            List<ChannelSeriesDto> serieses = seriesService.getSeriesByChnlUri(chnlUri, 1, "default",6);

            System.out.println("webtoons = " + webtoons);
            System.out.println("webnovel = " + webnovels);


            ChannelHomeDataDto channelHomeDto = ChannelHomeDataDto.builder().
                    channel(channel).
                    channelUser(user).
                    webtoons(webtoons).
                    webnovels(webnovels).
                    channelSerieses(serieses).
                    build();
//            List<ChannelHomeDataDto> data = new ArrayList<>();
            Map<String, Object>  data = new HashMap<>();
            data.put("data", channelHomeDto);

            return ResponseEntity.ok().body(data);
        }catch (Exception e){
            Map error = new HashMap();
            error.put("errMsg",e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createChannel(@AuthenticationPrincipal String userId, @RequestBody ChannelDto channelDto){

        try{
            List<ChannelDto> dtos = channelService.createChannel(userId, channelDto);

            return ResponseEntity.ok().body(dtos);

        }catch (Exception e){
            Map error = new HashMap();
            error.put("errMsg",e.getMessage());

            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/{chnlUri}/posts/{postType}")
    public ResponseEntity<?> retrievePosts(@PathVariable String chnlUri,@PathVariable String postType, @RequestParam(required = false, value = "page", defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "latest") String orderMethod,@RequestParam(required = false, value = "count", defaultValue = "12") int pageSize){

        try {
            List<ChannelPostDto> posts = postService.getPostsByChnlUri(chnlUri, postType ,page, orderMethod,pageSize);
            ChannelDto channel = channelService.retrieve(chnlUri);
            ChannelUserDto user = channelService.getUserByChannelUri(chnlUri);
            ChannelPostDataDto channelPostDataDto = null;

            if(postType.equals("webtoon")) {
                channelPostDataDto = ChannelPostDataDto.
                        builder().
                        channel(channel).
                        channelUser(user).
                        webtoons(posts).
                        build();
            } else if (postType.equals("webnovel")) {
                channelPostDataDto = ChannelPostDataDto.builder().channel(channel).channelUser(user).webnovels(posts).build();

            }
//            List<ChannelPostDataDto> data = new ArrayList<>();
//            data.add(channelSeriesDataDto);
            Map<String, Object>  data = new HashMap<>();
            data.put("data", channelPostDataDto);

            return ResponseEntity.ok().body(data);
        }catch (Exception e){
            Map error = new HashMap();
            error.put("errMsg",e.getMessage());

            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/{chnlUri}/series")
    public ResponseEntity<?> seriesList(@PathVariable String chnlUri, @RequestParam(required = false, value = "page", defaultValue = "1") int page
            , @RequestParam(required = false, defaultValue = "default") String orderMethod, @RequestParam(required = false, value = "count", defaultValue = "12") int pageSize){
        try {
            List<ChannelSeriesDto> serieses = seriesService.getSeriesByChnlUri(chnlUri, page, orderMethod, pageSize);
            ChannelDto channel = channelService.retrieve(chnlUri);
            ChannelUserDto user = channelService.getUserByChannelUri(chnlUri);

            ChannelSeriesDataDto channelSeriesDataDto = ChannelSeriesDataDto.builder().
                    channel(channel).
                    channelUser(user).
                    channelSerieses(serieses).
                    build();

            Map<String, Object>  data = new HashMap<>();
            data.put("data", channelSeriesDataDto);

            return ResponseEntity.ok().body(data);
        }catch (Exception e){
            Map error = new HashMap();
            error.put("errMsg",e.getMessage());

            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/{chnlUri}/about")
    public ResponseEntity<?> retrieveAbout(@PathVariable String chnlUri) {
        try {
            ChannelDto channel = channelService.retrieve(chnlUri);
            ChannelUserDto user = channelService.getUserByChannelUri(chnlUri);
            AboutDataDto about = AboutDataDto.builder().channel(channel).user(user).build();

            Map<String, Object>  data = new HashMap<>();
            data.put("data", about);
            return ResponseEntity.ok().body(data);

        } catch (Exception e) {
            Map error = new HashMap();
            error.put("errMsg",e.getMessage());

            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/{chnlUri}/series/{serId}")
    public ResponseEntity<?> retrieveSeries(@PathVariable String chnlUri, @PathVariable Integer serId,@RequestParam(required = false, value="count", defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "latest") String orderMethod,@RequestParam(required = false, value = "count", defaultValue = "12") int pageSize){

        try{

            SeriesDto series = seriesService.retrieveSeries(serId,page,orderMethod,pageSize);
            List<ChannelPostDto> posts = postService.getPostsBySerId(serId);
            ChannelUserDto user = channelService.getUserByChannelUri(chnlUri);
            SeriesContensDataDto seriesContents = SeriesContensDataDto.builder().series(series).posts(posts).user(user).build();

            Map<String, Object>  data = new HashMap<>();
            data.put("data", seriesContents);

            return ResponseEntity.ok().body(data);

        } catch (Exception e) {
            Map error = new HashMap();
            error.put("errMsg",e.getMessage());

            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/{chnlUri}/post/{postId}")
    public ResponseEntity<?> retrievePost(@AuthenticationPrincipal String userId, @PathVariable String chnlUri, @PathVariable Integer postId){

        try {
            ContentPostDto post = postService.readPostById(userId, postId);
            Map<String, Object> data = new HashMap<>();
            data.put("data", "");

            return ResponseEntity.ok().body(data);
        } catch (Exception e){
            Map error = new HashMap();
            error.put("errMsg", e.getMessage());

            return ResponseEntity.badRequest().body(error);
        }

    }

}