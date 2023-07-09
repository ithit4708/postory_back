package com.jungsuk_2_1.postory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChannelSimpleDto {
    private Integer chnlId;
    private String chnlUri;
    private String crtId;

    private String chnlTtl;
    private String chnlImgPath;

    private String chnlIntro;
}
