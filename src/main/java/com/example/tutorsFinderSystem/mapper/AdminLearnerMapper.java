package com.example.tutorsFinderSystem.mapper;

import com.example.tutorsFinderSystem.dto.response.AdminLearnerSummaryResponse;
import com.example.tutorsFinderSystem.dto.response.AdminLearnerDetailResponse;
import com.example.tutorsFinderSystem.entities.Learner;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdminLearnerMapper {

    // ===========================
    //  MAP SUMMARY (DANH SÁCH)
    // ===========================
    @Mapping(target = "user_id", source = "learner.user.userId")
    @Mapping(target = "full_name", source = "learner.user.fullName")
    @Mapping(target = "email", source = "learner.user.email")
    @Mapping(target = "phone_number", source = "learner.user.phoneNumber")
    @Mapping(target = "created_at", source = "learner.user.createdAt")
    @Mapping(target = "status", source = "learner.user.status")
    AdminLearnerSummaryResponse toSummary(Learner learner);


    // ===========================
    //  MAP DETAIL (CHI TIẾT)
    // ===========================
    @Mapping(target = "user_id", source = "learner.user.userId")
    @Mapping(target = "full_name", source = "learner.user.fullName")
    @Mapping(target = "email", source = "learner.user.email")
    @Mapping(target = "phone_number", source = "learner.user.phoneNumber")
    @Mapping(target = "address", source = "learner.address")
    @Mapping(target = "status", source = "learner.user.status")
    @Mapping(target = "created_at", source = "learner.user.createdAt")
    @Mapping(target = "updated_at", source = "learner.user.updatedAt")
    AdminLearnerDetailResponse toDetail(Learner learner);


}
