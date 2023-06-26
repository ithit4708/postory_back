package com.jungsuk_2_1.postory.controller;

import com.jungsuk_2_1.postory.dto.ChannelDto;
import com.jungsuk_2_1.postory.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

            return ResponseEntity.ok().body(dtos);

        }catch (Exception e){
            Map error = new HashMap();
            error.put("errMsg",e.getMessage());

            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@AuthenticationPrincipal String userId,@RequestBody ChannelDto channelDto){

        ChannelDto dto = channelService.update(userId,channelDto);
        List<ChannelDto> data = new ArrayList<>();
        data.add(dto);

        return ResponseEntity.ok().body(data);
    }
}
