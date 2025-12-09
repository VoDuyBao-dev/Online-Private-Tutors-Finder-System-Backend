package com.example.tutorsFinderSystem.mapper;

import com.example.tutorsFinderSystem.dto.response.LearnerResponse;
import com.example.tutorsFinderSystem.entities.Learner;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LearnerMapper {
    LearnerResponse toResponse(Learner learner);
}
