package com.jungsuk_2_1.postory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChannelPostDataDto {

    private List<ChannelPostDto> channelPosts;
    private ChannelUserDto channelUser;
    private ChannelDto channel;
}
