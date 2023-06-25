package com.jungsuk_2_1.postory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChannelSeriesDto {
    /* SER_ID */
    private Integer serId;

    /* SER_THUMN_PATH */
    private String serThumnPath;

    /* SER_TTL */
    private String serTtl;

    /* SER_DESC */
    private String serDesc;

    /* SER_OPEN_DTM */
    private String serOpenDtm;

    /* SER_INQR_CNT */
    private Integer serInqrCnt;

    /* SER_LIK_CNT */
    private Integer serLikCnt;

    /* SER_REP_CNT */
    private Integer serRepCnt;

    /* SER_POST_CNT */
    private Integer serPostCnt;

    /* CHNL_DSGNT_SER_ODR */
    private Integer chnlDsgntSerOdr;

    /* SER_STUS_CHG_DTM */
    private String serStusChgDtm;

    private String serStusCdNm;

    private String userNic;
}
