package com.example.tutorsFinderSystem.mapper;

import com.example.tutorsFinderSystem.dto.response.AdminTutorDetailResponse;
import com.example.tutorsFinderSystem.dto.response.AdminTutorPendingResponse;
import com.example.tutorsFinderSystem.dto.response.AdminTutorSummaryResponse;
import com.example.tutorsFinderSystem.entities.Tutor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdminTutorMapper {

    // Map summary: Tutor + danh sách môn + rating
    @Mapping(target = "user_id", source = "tutor.user.userId")
    @Mapping(target = "tutor_id", source = "tutor.tutorId")
    @Mapping(target = "full_name", source = "tutor.user.fullName")
    @Mapping(target = "status", source = "tutor.user.status")
    @Mapping(target = "updated_at", source = "tutor.user.updatedAt")
    @Mapping(target = "subjects", source = "subjectNames")
    @Mapping(target = "average_rating", source = "averageRating")
    // @Mapping(target = "tutor_code", expression = "java(buildTutorCode(tutor.getTutorId()))")
    AdminTutorSummaryResponse toSummaryResponse(Tutor tutor,
            List<String> subjectNames,
            Double averageRating);

    // Map chi tiết
    @Mapping(target = "user_id", source = "tutor.user.userId")
    @Mapping(target = "tutor_id", source = "tutor.tutorId")
    @Mapping(target = "full_name", source = "tutor.user.fullName")
    @Mapping(target = "email", source = "tutor.user.email")
    @Mapping(target = "phone_number", source = "tutor.user.phoneNumber")
    @Mapping(target = "status", source = "tutor.user.status")
    @Mapping(target = "updated_at", source = "tutor.user.updatedAt")
    @Mapping(target = "gender", source = "tutor.gender")
    @Mapping(target = "address", source = "tutor.address")
    @Mapping(target = "university", source = "tutor.university")
    @Mapping(target = "educational_level", source = "tutor.educationalLevel")
    @Mapping(target = "introduction", source = "tutor.introduction")
    @Mapping(target = "price_per_hour", source = "tutor.pricePerHour")
    @Mapping(target = "proof_file_url", source = "tutor.proofFileUrl")
    @Mapping(target = "verification_status", source = "tutor.verificationStatus")
    @Mapping(target = "subjects", source = "subjectNames")
    @Mapping(target = "certificates", source = "certificates")
    @Mapping(target = "average_rating", source = "averageRating")
    AdminTutorDetailResponse toDetailResponse(Tutor tutor,
            List<String> subjectNames,
            List<String> certificates,
            Double averageRating);

    @Mapping(target = "tutor_id", source = "tutor.tutorId")
    @Mapping(target = "user_id", source = "tutor.user.userId")
    @Mapping(target = "full_name", source = "tutor.user.fullName")
    @Mapping(target = "email", source = "tutor.user.email")
    @Mapping(target = "phone_number", source = "tutor.user.phoneNumber")
    @Mapping(target = "educational_level", source = "tutor.educationalLevel")
    @Mapping(target = "created_at", source = "tutor.user.createdAt")
    @Mapping(target = "subjects", source = "subjectNames")
    // @Mapping(target = "pending_code", expression = "java(buildPendingCode(tutor.getTutorId()))")
    AdminTutorPendingResponse toPendingResponse(Tutor tutor,
            List<String> subjectNames);

    // Default method dùng trong expression
    // default String buildTutorCode(Long tutorId) {
    //     if (tutorId == null)
    //         return null;
    //     return String.format("TUT%03d", tutorId);
    // }

    // default String buildPendingCode(Long tutorId) {
    //     if (tutorId == null)
    //         return null;
    //     return String.format("PEND%03d", tutorId);
    // }
}
