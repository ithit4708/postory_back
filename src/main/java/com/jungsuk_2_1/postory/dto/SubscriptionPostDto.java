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
public class SubscriptionPostDto {
    String chnlUri;
    String chnlId;
    String postId;
    String serTtl;
    String postTtl;
    String postSbTtl;
    String postContent;
    String postThumnPath;
    String userImgPath;
    String Nic;
    int postInqrCnt;
    int postLikCnt;
    Date postPblcDtm;
}
