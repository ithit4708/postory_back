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
    private String chnlUri;
    private String chnlId;
    private String postId;
    private String serTtl;
    private String postTtl;
    private String postSbTtl;
    private String postContent;
    private String postThumnPath;
    private String userImgPath;
    private String Nic;
    private int postInqrCnt;
    private int postLikCnt;
    private int postRepCnt;
    private Date postPblcDtm;
    private Boolean isScraped;
}
