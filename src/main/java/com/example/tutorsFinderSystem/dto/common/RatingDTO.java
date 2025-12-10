package com.example.tutorsFinderSystem.dto.common;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingDTO {
    private Long ratingId;
    private String learnerName;
    private String learnerAvatar;
    private Float score;
    private String comment;
    private LocalDateTime createdAt;
}