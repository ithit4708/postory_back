package com.jungsuk_2_1.postory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileDto {
    /* FILE_ID */
    private Integer fileId;

    /* POST_ID */
    private Integer postId;

    /* FILE_PATH */
    private String filePath;

    /* FILE_CAPCT */
    private Integer fileCapct;

    /* FILE_EXTNS */
    private String fileExtns;

    /* FILE_REG_DTM */
    private String fileRegDtm;

    private String URL;

    /* IMG_FILE_WID */
    private Integer imgFileWid;

    /* IMG_FILE_HG */
    private Integer imgFileHg;


}
