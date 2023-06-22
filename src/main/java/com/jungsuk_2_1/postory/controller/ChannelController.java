package com.jungsuk_2_1.postory.controller;

import com.jungsuk_2_1.postory.dto.ChannelDto;
import com.jungsuk_2_1.postory.dto.PostDto;
import com.jungsuk_2_1.postory.dto.ResponseDto;
import com.jungsuk_2_1.postory.service.ChannelService;
import com.jungsuk_2_1.postory.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("channel")
public class ChannelController{

    private final ChannelService channelService;
    private final PostService postService;

    @Autowired
    ChannelController(ChannelService channelService, PostService postService){
        this.channelService = channelService;
        this.postService = postService;
    }

    @GetMapping("/{chnlUri}")
    public ResponseEntity<?> retrieveChannel(@PathVariable(required = false) String chnlUri ){

        List<ChannelDto> dto = channelService.retrieve(chnlUri);

        ResponseDto<ChannelDto> response = ResponseDto.<ChannelDto>builder().data(dto).build();

        return ResponseEntity.ok().body(response);
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
    public ResponseEntity<?> retrievePosts(@PathVariable String chnlUri, @RequestParam(required = true) int page, @RequestParam(defaultValue = "latest") String orderMethod){

        List<PostDto> dtos = postService.getPostsByChnlUri(chnlUri, page, orderMethod);
        ResponseDto<PostDto> response = ResponseDto.<PostDto>builder().data(dtos).build();
        return ResponseEntity.badRequest().body(response);
    }
}
