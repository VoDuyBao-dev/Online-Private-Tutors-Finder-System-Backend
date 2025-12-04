package com.example.tutorsFinderSystem.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.tutorsFinderSystem.dto.response.TutorPersonalInfoResponse;
import com.example.tutorsFinderSystem.dto.response.TutorEducationResponse;
import com.example.tutorsFinderSystem.entities.Tutor;
import com.example.tutorsFinderSystem.entities.User;

@Mapper(componentModel = "spring")
public interface TutorProfileMapper {

    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "user.fullName", target = "fullName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.phoneNumber", target = "phoneNumber")
    TutorPersonalInfoResponse toPersonalInfo(User user, Tutor tutor);


    TutorEducationResponse toEducation(Tutor tutor);
}
