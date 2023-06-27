package com.jungsuk_2_1.postory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfilePostsDto {
    private String postTtl;
    private String postSbTtl;
    private String postContent;
    private int postRepCnt;
    private int postInqrCnt;
    private int postLikCnt;
    private Date postPblcDtm;
    private String postThumnPath;
    private String serTtl;
}
