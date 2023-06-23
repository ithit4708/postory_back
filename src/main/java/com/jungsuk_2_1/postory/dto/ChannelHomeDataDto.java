package com.jungsuk_2_1.postory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChannelHomeDataDto {
    private ChannelDto channel;
    private ChannelUserDto channelUser;
    private List<ChannelPostDto> channelPosts;
    private List<ChannelSeriesDto> channelSerieses;
}
