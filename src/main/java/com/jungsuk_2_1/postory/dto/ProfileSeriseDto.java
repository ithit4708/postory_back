package com.jungsuk_2_1.postory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileSeriseDto {
    private String serThumnPath;
    private String serTtl;
    private String serDesc;
    private String serInqrCnt;
    private String serLikCnt;
    private String serRepCnt;
    private String serPostCnt;
}
