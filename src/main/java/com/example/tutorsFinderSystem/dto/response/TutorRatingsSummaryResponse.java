package com.example.tutorsFinderSystem.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TutorRatingsSummaryResponse {
    private Double averageRating;
    private Long totalReviews;
}
