package com.jungsuk_2_1.postory.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StatusResponseDto {
    private HttpStatus status;
    private String message;
}