package com.jungsuk_2_1.postory.dto;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseDto {
    private List<?> data;
    private String errMsg;
}