package com.example.tutorsFinderSystem.mapper;

import com.example.tutorsFinderSystem.dto.response.AdminDetailPendingResponse;
import com.example.tutorsFinderSystem.dto.response.AdminTutorDetailResponse;
import com.example.tutorsFinderSystem.dto.response.AdminTutorPendingResponse;
import com.example.tutorsFinderSystem.dto.response.AdminTutorSummaryResponse;
import com.example.tutorsFinderSystem.dto.response.TutorEducationResponse;
import com.example.tutorsFinderSystem.entities.Tutor;
import com.example.tutorsFinderSystem.entities.TutorCertificate;
import com.example.tutorsFinderSystem.entities.TutorCertificateFile;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AdminTutorMapper {

    // 1. SUMMARY RESPONSE (dashboard list)
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


    // 2. DETAIL RESPONSE (Admin xem chi tiết hồ sơ)
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
    @Mapping(target = "verification_status", source = "tutor.verificationStatus")
    @Mapping(target = "subjects", source = "subjectNames")

    // Certificates – FULL DETAIL (NOT ONLY URL)
    @Mapping(target = "certificates", expression = "java(mapApprovedCertificatesDTO(tutor))")

    @Mapping(target = "average_rating", source = "averageRating")
    AdminTutorDetailResponse toDetailResponse(
            Tutor tutor,
            List<String> subjectNames,
            Double averageRating);


    // 3. PENDING RESPONSE (admin xét duyệt hồ sơ)
    @Mapping(target = "tutor_id", source = "tutor.tutorId")
    @Mapping(target = "user_id", source = "tutor.user.userId")
    @Mapping(target = "full_name", source = "tutor.user.fullName")
    @Mapping(target = "email", source = "tutor.user.email")
    @Mapping(target = "phone_number", source = "tutor.user.phoneNumber")
    @Mapping(target = "educational_level", source = "tutor.educationalLevel")
    @Mapping(target = "created_at", source = "tutor.user.createdAt")
    @Mapping(target = "subjects", source = "subjectNames")

    // FULL Pending certificates
    @Mapping(target = "pending_certificates", expression = "java(mapPendingCertificatesDTO(tutor))")
    AdminTutorPendingResponse toPendingResponse(
            Tutor tutor,
            List<String> subjectNames);


    // 4. DETAIL PENDING RESPONSE (full info + pending)
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

    // Lấy đúng file PENDING (FULL DTO)
    @Mapping(target = "certificates", expression = "java(mapPendingCertificatesDTO(tutor))")
    AdminDetailPendingResponse toDetailPendingResponse(
            Tutor tutor,
            List<String> subjectNames);


    // DEFAULT METHODS — FULL DTO FOR FILES

    // Convert TutorCertificate → CertificateDTO
    default List<TutorEducationResponse.CertificateDTO> mapCertificateListDTO(
            List<TutorCertificate> list,
            String statusFilter
    ) {
        if (list == null || list.isEmpty()) return List.of();

        return list.stream()
                .map(cert ->
                        TutorEducationResponse.CertificateDTO.builder()
                                .certificateId(cert.getCertificateId())
                                .certificateName(cert.getCertificateName())
                                .approved(cert.getApproved())
                                .files(mapFilesDTO(cert.getFiles(), statusFilter))
                                .build()
                )
                .filter(dto -> !dto.getFiles().isEmpty())
                .collect(Collectors.toList());
    }


    // Convert TutorCertificateFile → FileDTO
    default List<TutorEducationResponse.FileDTO> mapFilesDTO(List<TutorCertificateFile> files, String filterStatus) {
        if (files == null || files.isEmpty()) return List.of();

        return files.stream()
                .filter(f -> f.getStatus().name().equalsIgnoreCase(filterStatus))
                .map(f ->
                        TutorEducationResponse.FileDTO.builder()
                                .fileId(f.getFileId())
                                .fileUrl(toPreviewUrl(f.getFileUrl()))
                                .status(f.getStatus().name())
                                .isActive(f.getIsActive())
                                .uploadedAt(f.getUploadedAt() == null ? null : f.getUploadedAt().toString())
                                .build()
                )
                .toList();
    }



    // 5. FILTER METHODS (APPROVED / PENDING / REJECTED)

    default List<TutorEducationResponse.CertificateDTO> mapApprovedCertificatesDTO(Tutor tutor) {
        return mapCertificateListDTO(tutor.getCertificates(), "APPROVED");
    }

    default List<TutorEducationResponse.CertificateDTO> mapPendingCertificatesDTO(Tutor tutor) {
        return mapCertificateListDTO(tutor.getCertificates(), "PENDING");
    }

    default List<TutorEducationResponse.CertificateDTO> mapRejectedCertificatesDTO(Tutor tutor) {
        return mapCertificateListDTO(tutor.getCertificates(), "REJECTED");
    }


    // 6. FIX GOOGLE DRIVE PREVIEW
    default String toPreviewUrl(String url) {
        if (url == null) return null;

        // ?id=<id>
        if (url.contains("id=")) {
            String id = url.substring(url.indexOf("id=") + 3);
            return "https://drive.google.com/file/d/" + id + "/preview";
        }

        // file/d/<id>/view
        if (url.contains("/file/d/")) {
            String id = url.split("/file/d/")[1].split("/")[0];
            return "https://drive.google.com/file/d/" + id + "/preview";
        }

        // open?id=<id>
        if (url.contains("open?id=")) {
            String id = url.substring(url.indexOf("open?id=") + 8);
            return "https://drive.google.com/file/d/" + id + "/preview";
        }

        return url;
    }
}
