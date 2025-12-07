package com.example.tutorsFinderSystem.mapper;

import com.example.tutorsFinderSystem.dto.response.AdminDetailPendingResponse;
import com.example.tutorsFinderSystem.dto.response.AdminTutorDetailResponse;
import com.example.tutorsFinderSystem.dto.response.AdminTutorPendingResponse;
import com.example.tutorsFinderSystem.dto.response.AdminTutorSummaryResponse;
import com.example.tutorsFinderSystem.entities.Tutor;
import com.example.tutorsFinderSystem.entities.TutorCertificate;
import com.example.tutorsFinderSystem.entities.TutorCertificateFile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AdminTutorMapper {

        // ============================================
        // 1. SUMMARY RESPONSE (dashboard list)
        // ============================================
        @Mapping(target = "user_id", source = "tutor.user.userId")
        @Mapping(target = "tutor_id", source = "tutor.tutorId")
        @Mapping(target = "full_name", source = "tutor.user.fullName")
        @Mapping(target = "status", source = "tutor.user.status")
        @Mapping(target = "updated_at", source = "tutor.user.updatedAt")
        @Mapping(target = "subjects", source = "subjectNames")
        @Mapping(target = "average_rating", source = "averageRating")
        AdminTutorSummaryResponse toSummaryResponse(
                        Tutor tutor,
                        List<String> subjectNames,
                        Double averageRating);

        // ============================================
        // 2. DETAIL RESPONSE (Admin xem chi tiết hồ sơ)
        // ============================================
        @Mapping(target = "user_id", source = "tutor.user.userId")
        @Mapping(target = "tutor_id", source = "tutor.tutorId")
        @Mapping(target = "full_name", source = "tutor.user.fullName")
        @Mapping(target = "email", source = "tutor.user.email")
        @Mapping(target = "phone_number", source = "tutor.user.phoneNumber")
        @Mapping(target = "status", source = "tutor.user.status")
        @Mapping(target = "updated_at", source = "tutor.user.updatedAt")
        // @Mapping(target = "updated_at",expression = "java( tutor.getUser().getStatus() == UserStatus.INACTIVE ? tutor.getUser().getCreatedAt() : (tutor.getUser().getUpdatedAt() != null ? tutor.getUser().getUpdatedAt() : tutor.getUser().getCreatedAt()) )")
        @Mapping(target = "gender", source = "tutor.gender")
        @Mapping(target = "address", source = "tutor.address")
        @Mapping(target = "university", source = "tutor.university")
        @Mapping(target = "educational_level", source = "tutor.educationalLevel")
        @Mapping(target = "introduction", source = "tutor.introduction")
        @Mapping(target = "price_per_hour", source = "tutor.pricePerHour")

        @Mapping(target = "verification_status", source = "tutor.verificationStatus")
        @Mapping(target = "subjects", source = "subjectNames")

        // Certificates – LẤY FILE ĐÃ DUYỆT
        @Mapping(target = "certificates", expression = "java(mapApprovedCertificates(tutor))")

        // Ratings
        @Mapping(target = "average_rating", source = "averageRating")
        AdminTutorDetailResponse toDetailResponse(
                        Tutor tutor,
                        List<String> subjectNames,
                        Double averageRating);

        // ============================================
        // 3. PENDING RESPONSE (admin xét duyệt hồ sơ mới)
        // ============================================
        @Mapping(target = "tutor_id", source = "tutor.tutorId")
        @Mapping(target = "user_id", source = "tutor.user.userId")
        @Mapping(target = "full_name", source = "tutor.user.fullName")
        @Mapping(target = "email", source = "tutor.user.email")
        @Mapping(target = "phone_number", source = "tutor.user.phoneNumber")
        @Mapping(target = "educational_level", source = "tutor.educationalLevel")
        @Mapping(target = "created_at", source = "tutor.user.createdAt")
        @Mapping(target = "subjects", source = "subjectNames")

        // Lấy danh sách FILE CHỜ DUYỆT
        @Mapping(target = "pending_certificates", expression = "java(mapPendingCertificates(tutor))")
        AdminTutorPendingResponse toPendingResponse(
                        Tutor tutor,
                        List<String> subjectNames);



         @Mapping(target = "user_id", source = "tutor.user.userId")
        @Mapping(target = "tutor_id", source = "tutor.tutorId")
        @Mapping(target = "full_name", source = "tutor.user.fullName")
        @Mapping(target = "email", source = "tutor.user.email")
        @Mapping(target = "phone_number", source = "tutor.user.phoneNumber")
        @Mapping(target = "status", source = "tutor.user.status")
        @Mapping(target = "create_at", source = "tutor.user.createdAt")
        @Mapping(target = "gender", source = "tutor.gender")
        @Mapping(target = "address", source = "tutor.address")
        @Mapping(target = "university", source = "tutor.university")
        @Mapping(target = "educational_level", source = "tutor.educationalLevel")
        @Mapping(target = "introduction", source = "tutor.introduction")
        @Mapping(target = "price_per_hour", source = "tutor.pricePerHour")

        @Mapping(target = "verification_status", source = "tutor.verificationStatus")
        @Mapping(target = "subjects", source = "subjectNames")

        // Certificates – LẤY FILE ĐÃ DUYỆT
        @Mapping(target = "certificates", expression = "java(mapPendingCertificates(tutor))")

        // Ratings
        AdminDetailPendingResponse toDetailPendingResponse(
                        Tutor tutor,
                        List<String> subjectNames);
        // ===================================================
        // DEFAULT METHODS – LẤY 3 LOẠI FILE
        // ===================================================

        // 1. LẤY FILE PENDING
        default List<String> mapPendingCertificates(Tutor tutor) {
                return tutor.getCertificates().stream()
                                .flatMap(cert -> cert.getFiles().stream()
                                                .filter(f -> f.getStatus().name().equals("PENDING"))
                                                .map(TutorCertificateFile::getFileUrl))
                                .collect(Collectors.toList());
        }

        // 2. LẤY FILE APPROVED
        default List<String> mapApprovedCertificates(Tutor tutor) {
                return tutor.getCertificates().stream()
                                .flatMap(cert -> cert.getFiles().stream()
                                                .filter(f -> f.getStatus().name().equals("APPROVED"))
                                                .map(TutorCertificateFile::getFileUrl))
                                .collect(Collectors.toList());
        }

        // 3. LẤY FILE REJECTED
        default List<String> mapRejectedCertificates(Tutor tutor) {
                return tutor.getCertificates().stream()
                                .flatMap(cert -> cert.getFiles().stream()
                                                .filter(f -> f.getStatus().name().equals("REJECTED"))
                                                .map(TutorCertificateFile::getFileUrl))
                                .collect(Collectors.toList());
        }
}
