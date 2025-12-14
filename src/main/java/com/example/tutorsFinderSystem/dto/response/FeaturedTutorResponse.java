package com.example.tutorsFinderSystem.dto.response;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeaturedTutorResponse {
    private Long tutorId;
    private String fullName;
    private String avatarUrl;
    private String subject;
    private Double averageRating;
    private Long totalRatings;
    private Integer pricePerHour;
    private String address;
}
