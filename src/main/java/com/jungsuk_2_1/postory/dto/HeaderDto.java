package com.jungsuk_2_1.postory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HeaderDto {
    private String token;
    private String userStatus;
    private String userImgPath;
    private String nic;
    private String chnlId;
    private String chnlUrl;
    private String chnlTtl;
}
