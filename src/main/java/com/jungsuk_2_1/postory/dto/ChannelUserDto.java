package com.jungsuk_2_1.postory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChannelUserDto {
    private String eid; // 아이디로 사용할 유저네임. 이메일일 수도 그냥 문자열일 수도 있다.
    private String nic;
    private String userIntro;
    private String userImgPath;
    private boolean msgAlowYn = true;
}
