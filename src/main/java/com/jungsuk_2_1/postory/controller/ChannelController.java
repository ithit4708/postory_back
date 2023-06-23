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
import java.util.List;

@RestController
@RequestMapping("channel")
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
            List<ChannelPostDto> posts = postService.getPostsByChnlUri(chnlUri, 1, "latest",6);
            List<ChannelSeriesDto> serieses = seriesService.getSeriesByChnlUri(chnlUri, 1, "default",6);


            ChannelHomeDataDto channelHomeDto = ChannelHomeDataDto.builder().
                    channel(channel).
                    channelPosts(posts).
                    channelUser(user).
                    channelSerieses(serieses).
                    build();
            List<ChannelHomeDataDto> data = new ArrayList<>();
            data.add(channelHomeDto);

            ResponseDto<ChannelHomeDataDto> response = ResponseDto.<ChannelHomeDataDto>builder().data(data).build();

            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            String error = e.getMessage();

            ResponseDto<ChannelDto> response = ResponseDto.<ChannelDto>builder().error(error).build();

            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createChannel(@AuthenticationPrincipal String userId, @RequestBody ChannelDto channelDto){

        try{
            List<ChannelDto> dtos = channelService.createChannel(userId, channelDto);
            ResponseDto<ChannelDto> response = ResponseDto.<ChannelDto>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);

        }catch (Exception e){

            String error = e.getMessage();

            ResponseDto<ChannelDto> response = ResponseDto.<ChannelDto>builder().error(error).build();

            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/{chnlUri}/posts")
    public ResponseEntity<?> retrievePosts(@PathVariable String chnlUri, @RequestParam(required = false, value="count", defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "latest") String orderMethod,@RequestParam(required = false, value = "count", defaultValue = "12") int pageSize){

        try {
            List<ChannelPostDto> posts = postService.getPostsByChnlUri(chnlUri, page, orderMethod,pageSize);
            ChannelDto channel = channelService.retrieve(chnlUri);
            ChannelUserDto user = channelService.getUserByChannelUri(chnlUri);

            ChannelPostDataDto channelSeriesDataDto = ChannelPostDataDto.
                    builder().
                    channel(channel).
                    channelUser(user).
                    channelPosts(posts).
                    build();
            List<ChannelPostDataDto> data = new ArrayList<>();
            data.add(channelSeriesDataDto);

            ResponseDto<ChannelPostDataDto> response = ResponseDto.<ChannelPostDataDto>builder().data(data).build();
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            String error = e.getMessage();
            ResponseDto<ChannelDto> response = ResponseDto.<ChannelDto>builder().error(error).build();

            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/{chnlUri}/series")
    public ResponseEntity<?> retrieveSeries(@PathVariable String chnlUri, @RequestParam(required = false, value = "count", defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "default") String orderMethod, @RequestParam(required = false, value = "count", defaultValue = "12") int pageSize){
        try {
            List<ChannelSeriesDto> serieses = seriesService.getSeriesByChnlUri(chnlUri, page, orderMethod, pageSize);
            ChannelDto channel = channelService.retrieve(chnlUri);
            ChannelUserDto user = channelService.getUserByChannelUri(chnlUri);

            ChannelSeriesDataDto channelSeriesDataDto = ChannelSeriesDataDto.builder().
                    channel(channel).
                    channelUser(user).
                    channelSerieses(serieses).
                    build();
            List<ChannelSeriesDataDto> data = new ArrayList<>();
            data.add(channelSeriesDataDto);

            ResponseDto<ChannelSeriesDataDto> response = ResponseDto.<ChannelSeriesDataDto>builder().data(data).build();
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            String error = e.getMessage();
            ResponseDto<ChannelDto> response = ResponseDto.<ChannelDto>builder().error(error).build();

            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/{chnlUri}/about")
    public ResponseEntity<?> retrieveAbout(@PathVariable String chnlUri) {
        try {
            ChannelDto channel = channelService.retrieve(chnlUri);
            ChannelUserDto user = channelService.getUserByChannelUri(chnlUri);
            AboutDataDto about = AboutDataDto.builder().channel(channel).user(user).build();
            List<AboutDataDto> data = new ArrayList<>();
            data.add(about);

            ResponseDto<AboutDataDto> response = ResponseDto.<AboutDataDto>builder().data(data).build();
            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDto<ChannelDto> response = ResponseDto.<ChannelDto>builder().error(error).build();

            return ResponseEntity.badRequest().body(response);
        }
    }
}
