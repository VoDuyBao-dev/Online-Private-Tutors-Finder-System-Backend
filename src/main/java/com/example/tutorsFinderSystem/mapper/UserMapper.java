package com.example.tutorsFinderSystem.mapper;

import com.example.tutorsFinderSystem.dto.request.LearnerRequest;
import com.example.tutorsFinderSystem.dto.response.TutorPersonalInfoResponse;
import com.example.tutorsFinderSystem.entities.Tutor;
import com.example.tutorsFinderSystem.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(LearnerRequest learnerRequest);

}
