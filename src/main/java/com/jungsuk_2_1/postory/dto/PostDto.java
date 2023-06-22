package com.jungsuk_2_1.postory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    /* POST_ID */
    private Integer postId;

    /* POST_TTL */
    private String postTtl;

    /* POST_SB_TTL */
    private String postSbTtl;

    /* POST_INQR_CNT */
    private Integer postInqrCnt;

    /* POST_REP_CNT */
    private Integer postRepCnt;

    /* POST_LIK_CNT */
    private Integer postLikCnt;

    /* SPAC_INCL_CHAR_CNT */
    private Integer spacInclCharCnt;

    /* POST_PCHRG_YN */
    private Object postPchrgYn;

    /* POST_THUMN_PATH */
    private String postThumnPath;

    /* SER_ID */
    private Integer serId;

    /* OPUB_PLAN_ID */
    private Integer opubPlanId;

    /* PCHRG_BLK_PURC_PNT */
    private Integer pchrgBlkPurcPnt;

    /* NTCE_SETT_YN */
    private Object ntceSettYn;

    /* ADO_YN */
    private Object adoYn;

    /* POST_PBLC_DTM */
    private String postPblcDtm;

    /* CHNL_ID */
    private Integer chnlId;

    /* BASIC_FONT_CD */
    private Object basicFontCd;

    /* BASIC_PARAG_ALGN_CD */
    private Object basicParagAlgnCd;

    /* ITD_YN */
    private Object itdYn;

    /* PARAG_GAP_MARG_YN */
    private Object paragGapMargYn;

    /* NOW_POST_STUS_CD */
    private Object nowPostStusCd;

    /* NOW_POST_STUD_CHG_DTM */
    private String nowPostStudChgDtm;

    /* NOW_POST_STUS_CHGR_ID */
    private String nowPostStusChgrId;

    /* BEF_POST_ID */
    private Integer befPostId;

    /* NEXT_POST_ID */
    private Integer nextPostId;

}
