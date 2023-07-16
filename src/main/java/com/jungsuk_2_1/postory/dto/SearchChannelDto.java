package com.jungsuk_2_1.postory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class SearchChannelDto {
    private String chnlImgPath;
    private String chnlTtl;
    private String chnlIntro;
    private String chnlUri;
    private String chnlId;
    private Boolean isSubsed;
    private Integer suberCnt;
}
