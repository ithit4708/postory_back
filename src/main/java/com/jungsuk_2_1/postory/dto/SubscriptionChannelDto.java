package com.jungsuk_2_1.postory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionChannelDto {
    private String chnlUri;
    private String chnlId;
    private String chnlImgPath;
    private String chnlTtl;
    private String chnlIntro;
    private String userImgPath;
    private String nic;
    private Boolean isSubsed;
}
