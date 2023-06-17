package com.jungsuk_2_1.postory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChannelDto {
    /* CHNL_ID */
    private Integer chnlId;

    /* CHNL_TTL */
    private String chnlTtl;

    /* CHNL_INTRO */
    private String chnlIntro;

    /* CHNL_URI */
    private String chnlUri;

    /* CHNL_SB_TTL */
    private String chnlSbTtl;

    /* CHNL_IMG_PATH */
    private String chnlImgPath;

    /* SUBER_CNT */
    private Integer suberCnt;

    /* CHNL_POST_CNT */
    private Integer chnlPostCnt;

    /* CHNL_LIK_CNT */
    private Integer chnlLikCnt;

    /* CHNL_OPEN_DTM */
    private String chnlOpenDtm;

    /* CRT_ID */
    private String crtId;

    /* HMPG_URL */
    private String hmpgUrl;

    /* INSTA_URL */
    private String instaUrl;

    /* GITH_URL */
    private String githUrl;

    /* YTB_URL */
    private String ytbUrl;

    /* TWCH_URL */
    private String twchUrl;

    /* CHNL_STUS_CD */
    private Object chnlStusCd;

    /* CHNL_STUS_CHGR_ID */
    private String chnlStusChgrId;

    /* CHNL_STUS_CHG_DTM */
    private String chnlStusChgDtm;

}
