package com.jungsuk_2_1.postory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeriesDto {
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

    /* RECEN_PBLC_POST_ID */
    private Integer recenPblcPostId;

    /* CHNL_ID */
    private Integer chnlId;

    /* SER_STUS_CD */
    private String serStusCdNm;

    /* SER_STUS_CHGR_ID */
    private String serStusChgrId;

    /* SER_STUS_CHG_DTM */
    private String serStusChgDtm;

    private String chnlUri;
}
