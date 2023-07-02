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
    String chnlUri;
    String chnlId;
    String chnlImgPath;
    String chnlTtl;
    String chnlIntro;
    String userImgPath;
    String nic;
}
