package com.example.tutorsFinderSystem.dto.common;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingStatisticsDTO {
    private Double averageScore;  // Điểm trung bình
    private Long totalReviews;     // Tổng số đánh giá
    private Long fiveStarCount;    // Số lượng 5 sao
    private Long fourStarCount;    // Số lượng 4 sao
    private Long threeStarCount;   // Số lượng 3 sao
    private Long twoStarCount;     // Số lượng 2 sao
    private Long oneStarCount;     // Số lượng 1 sao
}