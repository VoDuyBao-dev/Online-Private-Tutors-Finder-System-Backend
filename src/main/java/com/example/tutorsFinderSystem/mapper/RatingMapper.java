package com.example.tutorsFinderSystem.mapper;

import com.example.tutorsFinderSystem.dto.common.RatingDTO;
import com.example.tutorsFinderSystem.entities.Ratings;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RatingMapper {
    @Mapping(target = "ratingId", source = "rating.ratingId")
    @Mapping(target = "score", source = "rating.score")
    @Mapping(target = "comment", source = "rating.comment")
    @Mapping(target = "createdAt", source = "rating.createdAt")
    @Mapping(target = "learnerName",
            expression = "java(rating.getClassEntity().getClassRequest().getLearner().getFullName())")
    @Mapping(target = "learnerAvatar",
            expression = "java(rating.getClassEntity().getClassRequest().getLearner().getUser().getAvatarImage())")
    RatingDTO toRatingDTO(Ratings rating);
}
