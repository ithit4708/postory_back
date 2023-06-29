package com.jungsuk_2_1.postory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudioSeriesDto {
    /* SER_ID */
    private Integer serId;

    /* SER_THUMN_PATH */
    private String serThumnPath;

    /* SER_TTL */
    private String serTtl;

    private String serOpenDtm;

    private Integer serPostCnt;

    private Integer chnlDsgntSerOdr;

    private Object serStusCdNm;
    private String serStusChgDtm;
}
