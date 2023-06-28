package com.jungsuk_2_1.postory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudioPostDto {
    private Integer postId;
    private String postTtl;
    private Integer postInqrCnt;

    private Integer postRepCnt;

    private Integer postLikCnt;

    private Object postPchrgYn;

    private String postThumnPath;

    private Integer serId;

    private Integer opubPlanId;

    private String postPblcDtm;

    private Integer chnlId;

    private Object nowPostStusCd;

    private String nowPostStudChgDtm;

    private String serTtl;
    private String userNic;
    private String nowPostStusNm;
}
