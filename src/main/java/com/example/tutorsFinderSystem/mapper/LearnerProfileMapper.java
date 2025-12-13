package com.example.tutorsFinderSystem.mapper;

import com.example.tutorsFinderSystem.dto.response.LearnerProfileResponse;
import com.example.tutorsFinderSystem.entities.Learner;
import com.example.tutorsFinderSystem.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LearnerProfileMapper {

    @Mapping(target = "fullName", source = "user.fullName")
    @Mapping(target = "phoneNumber", source = "user.phoneNumber")
    @Mapping(target = "email", source = "user.email")
    // @Mapping(target = "roleLabel", expression = "java(\"Người học\")")
    // @Mapping(target = "address", expression = "java(buildFullAddress(learner))")
    @Mapping(target = "address", source = "learner.address")
    LearnerProfileResponse toResponse(Learner learner, User user);

    // default String buildFullAddress(Learner learner) {
    //     if (learner == null) return null;

    //     String address = learner.getAddress();
    //     String detail = learner.getAddressDetail();

    //     if (detail == null || detail.isBlank()) {
    //         return address;
    //     }
    //     if (address == null || address.isBlank()) {
    //         return detail;
    //     }
    //     return detail + ", " + address;
    // }
}
