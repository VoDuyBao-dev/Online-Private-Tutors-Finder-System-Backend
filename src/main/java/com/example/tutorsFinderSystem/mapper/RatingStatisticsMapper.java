package com.example.tutorsFinderSystem.mapper;

import com.example.tutorsFinderSystem.dto.common.RatingStatisticsDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RatingStatisticsMapper {
    RatingStatisticsDTO toRatingStatisticsDTO(Double averageScore,
                                              Long totalReviews,
                                              Long fiveStarCount,
                                              Long fourStarCount,
                                              Long threeStarCount,
                                              Long twoStarCount,
                                              Long oneStarCount);
}
