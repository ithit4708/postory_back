package com.jungsuk_2_1.postory.controller;

import com.jungsuk_2_1.postory.dto.ChannelDto;
import com.jungsuk_2_1.postory.dto.ResponseDto;
import com.jungsuk_2_1.postory.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("studio/{chnlUri}")
public class StudioController {
    ChannelService channelService;

    @Autowired
    StudioController(ChannelService channelService){
        this.channelService = channelService;
    }

    @DeleteMapping
    public ResponseEntity<?> deleteChannel(@AuthenticationPrincipal String userId, @RequestParam String chnlUri){

        try{

            List<ChannelDto> dtos = channelService.delete(userId, chnlUri);


            ResponseDto<ChannelDto> response = ResponseDto.<ChannelDto>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);

        }catch (Exception e){
            String error = e.getMessage();

            ResponseDto<ChannelDto> response = ResponseDto.<ChannelDto>builder().error(error).build();

            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@AuthenticationPrincipal String userId,@RequestBody ChannelDto channelDto){

        ChannelDto dto = channelService.update(userId,channelDto);
        List<ChannelDto> data = new ArrayList<>();
        data.add(dto);

        ResponseDto<ChannelDto> response = ResponseDto.<ChannelDto>builder().data(data).build();

        return ResponseEntity.ok().body(response);
    }
}
