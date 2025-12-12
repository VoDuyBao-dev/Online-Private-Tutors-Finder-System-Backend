package com.example.tutorsFinderSystem.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingRequest {
    private Long classId;
    private Float score;
    private String comment;
}
