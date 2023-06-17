package com.jungsuk_2_1.postory.controller;

import com.jungsuk_2_1.postory.dto.ChannelDto;
import com.jungsuk_2_1.postory.dto.ResponseDto;
import com.jungsuk_2_1.postory.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("channel")
public class ChannelController {

    private final ChannelService channelService;

    @Autowired
    ChannelController(ChannelService channelService){
        this.channelService = channelService;
    }

    @GetMapping("/channels")
    public List<ChannelDto> getChannels(){
        return channelService.getChannels();
    }



    @PostMapping("/create")
    public ResponseEntity<?> createChannel(@RequestBody ChannelDto channelDto){

        try{
            String temporaryUserId = "772d132622ba4b53953e251b254671f";

            channelDto.setCrtId(temporaryUserId);


            List<ChannelDto> dtos = channelService.createChannel(channelDto);


            ResponseDto<ChannelDto> response = ResponseDto.<ChannelDto>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);


        }catch (Exception e){

            String error = e.getMessage();

            ResponseDto<ChannelDto> response = ResponseDto.<ChannelDto>builder().error(error).build();

            return ResponseEntity.badRequest().body(response);
        }

    }

    @GetMapping("/user")
    public ResponseEntity<?> retrieveChannelDto(@RequestParam String crtId){
        List<ChannelDto> dtos = channelService.retrieve(crtId);


        ResponseDto<ChannelDto> response = ResponseDto.<ChannelDto>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteChannel(@RequestBody ChannelDto channelDto){

        try{


            List<ChannelDto> dtos = channelService.delete(channelDto);


            ResponseDto<ChannelDto> response = ResponseDto.<ChannelDto>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);

        }catch (Exception e){
            String error = e.getMessage();

            ResponseDto<ChannelDto> response = ResponseDto.<ChannelDto>builder().error(error).build();

            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateTodo(@RequestBody ChannelDto channelDto){


        List<ChannelDto> dtos = channelService.update(channelDto);


        ResponseDto<ChannelDto> response = ResponseDto.<ChannelDto>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }






}
