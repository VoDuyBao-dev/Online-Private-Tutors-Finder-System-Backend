package com.example.tutorsFinderSystem.mapper;

import com.example.tutorsFinderSystem.dto.response.TutorEducationResponse;
import com.example.tutorsFinderSystem.dto.response.TutorPersonalInfoResponse;
import com.example.tutorsFinderSystem.entities.Tutor;
import com.example.tutorsFinderSystem.entities.TutorCertificate;
import com.example.tutorsFinderSystem.entities.TutorCertificateFile;
import com.example.tutorsFinderSystem.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TutorProfileMapper {

    DateTimeFormatter DATE_TIME_FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // =================================================
    // 1. PERSONAL INFO
    // =================================================
    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "user.fullName", target = "fullName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.phoneNumber", target = "phoneNumber")
    @Mapping(target = "avatarUrl", expression = "java(buildAvatarUrl(user))")
    TutorPersonalInfoResponse toPersonalInfo(User user, Tutor tutor);

    // =================================================
    // 2. EDUCATION
    // =================================================
    default TutorEducationResponse toEducation(Tutor tutor) {
        return TutorEducationResponse.builder()
                .tutorId(tutor.getTutorId())
                .university(tutor.getUniversity())
                .introduction(tutor.getIntroduction())
                .pricePerHour(tutor.getPricePerHour())
                .certificates(mapCertificates(tutor.getCertificates()))
                .build();
    }

    // =================================================
    // 3. CERTIFICATES
    // =================================================
    default List<TutorEducationResponse.CertificateDTO> mapCertificates(
            List<TutorCertificate> certificates) {

        if (certificates == null || certificates.isEmpty())
            return List.of();

        return certificates.stream()
                .map(cert -> TutorEducationResponse.CertificateDTO.builder()
                        .certificateId(cert.getCertificateId())
                        .certificateName(cert.getCertificateName())
                        .approved(cert.getApproved())
                        .files(mapFiles(cert.getFiles()))
                        .build())
                .collect(Collectors.toList());
    }

    // =================================================
    // 4. FILES (SORT + FORMAT)
    // =================================================
    default List<TutorEducationResponse.FileDTO> mapFiles(
            List<TutorCertificateFile> files) {

        if (files == null || files.isEmpty())
            return List.of();

        return files.stream()
                // Ưu tiên file active trước
                .sorted(Comparator
                        .comparing(TutorCertificateFile::getIsActive,
                                Comparator.nullsLast(Boolean::compareTo))
                        .reversed()
                        .thenComparing(TutorCertificateFile::getUploadedAt,
                                Comparator.nullsLast(Comparator.reverseOrder()))
                )
                .map(f -> TutorEducationResponse.FileDTO.builder()
                        .fileId(f.getFileId())
                        .fileUrl(toPreviewUrl(f.getFileUrl()))
                        .status(f.getStatus().name())
                        .isActive(f.getIsActive())
                        .uploadedAt(
                                f.getUploadedAt() == null
                                        ? null
                                        : f.getUploadedAt().format(DATE_TIME_FMT)
                        )
                        .build())
                .collect(Collectors.toList());
    }

    // =================================================
    // 5. AVATAR URL (GIỮ NGUYÊN)
    // =================================================
    default String buildAvatarUrl(User user) {
        if (user == null || user.getAvatarImage() == null)
            return null;

        String avatar = user.getAvatarImage();

        if (!avatar.contains("http")) {
            return "http://localhost:8080/tutorsFinder/drive/view/" + avatar;
        }

        if (avatar.contains("id=")) {
            String id = avatar.substring(avatar.indexOf("id=") + 3);
            int idx = id.indexOf("&");
            if (idx != -1) id = id.substring(0, idx);

            return "http://localhost:8080/tutorsFinder/drive/view/" + id;
        }

        return avatar;
    }

    // =================================================
    // 6. GOOGLE DRIVE → PREVIEW URL (FIX CHUẨN)
    // =================================================
    default String toPreviewUrl(String url) {
        if (url == null)
            return null;

        // Nếu đã là preview
        if (url.contains("/preview")) {
            return url;
        }

        String fileId = null;

        // uc?id=
        if (url.contains("id=")) {
            fileId = url.substring(url.indexOf("id=") + 3);
        }
        // /file/d/{id}/
        else if (url.contains("/file/d/")) {
            fileId = url.split("/file/d/")[1].split("/")[0];
        }
        // open?id=
        else if (url.contains("open?id=")) {
            fileId = url.substring(url.indexOf("open?id=") + 8);
        }

        if (fileId != null) {
            int idx = fileId.indexOf("&");
            if (idx != -1) {
                fileId = fileId.substring(0, idx);
            }
            return "https://drive.google.com/file/d/" + fileId + "/preview";
        }

        return url;
    }
}
