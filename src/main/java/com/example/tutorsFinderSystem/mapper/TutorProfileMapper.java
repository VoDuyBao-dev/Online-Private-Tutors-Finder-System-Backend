package com.example.tutorsFinderSystem.mapper;

import com.example.tutorsFinderSystem.dto.response.TutorEducationResponse;
import com.example.tutorsFinderSystem.dto.response.TutorPersonalInfoResponse;
import com.example.tutorsFinderSystem.entities.Tutor;
import com.example.tutorsFinderSystem.entities.TutorCertificate;
import com.example.tutorsFinderSystem.entities.TutorCertificateFile;
import com.example.tutorsFinderSystem.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TutorProfileMapper {

    // 1. PERSONAL INFO
    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "user.fullName", target = "fullName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.phoneNumber", target = "phoneNumber")
    @Mapping(target = "avatarUrl", expression = "java(buildAvatarUrl(user))")
    TutorPersonalInfoResponse toPersonalInfo(User user, Tutor tutor);

    default TutorEducationResponse toEducation(Tutor tutor) {
        return TutorEducationResponse.builder()
                .tutorId(tutor.getTutorId())
                .university(tutor.getUniversity())
                .introduction(tutor.getIntroduction())
                .pricePerHour(tutor.getPricePerHour())
                .certificates(mapCertificates(tutor.getCertificates()))
                .build();
    }

    // 2. CERTIFICATES MAPPING
    default List<TutorEducationResponse.CertificateDTO> mapCertificates(List<TutorCertificate> certificates) {
        if (certificates == null || certificates.isEmpty())
            return List.of();

        return certificates.stream()
                .map(cert -> TutorEducationResponse.CertificateDTO.builder()
                        .certificateId(cert.getCertificateId())
                        .certificateName(cert.getCertificateName())
                        .approved(cert.getApproved())
                        .files(mapFiles(cert.getFiles()))
                        .build())
                .toList();
    }

    // 3. FILES MAPPING
    default List<TutorEducationResponse.FileDTO> mapFiles(List<TutorCertificateFile> files) {
        if (files == null || files.isEmpty())
            return List.of();

        return files.stream()
                .map(f -> TutorEducationResponse.FileDTO.builder()
                        .fileId(f.getFileId())
                        .fileUrl(toPreviewUrl(f.getFileUrl()))
                        .status(f.getStatus().name())
                        .isActive(f.getIsActive())
                        .uploadedAt(f.getUploadedAt() == null ? null : f.getUploadedAt().toString())
                        .build())
                .collect(Collectors.toList());
    }

    default String buildAvatarUrl(User user) {
        if (user == null || user.getAvatarImage() == null)
            return null;

        String avatar = user.getAvatarImage();

        // Trường hợp DB lưu fileId thuần → backend xử lý
        if (!avatar.contains("http")) {
            return "http://localhost:8080/tutorsFinder/drive/view/" + avatar;
        }

        // Nếu là URL Drive dạng ?id=xxxx
        if (avatar.contains("id=")) {
            String id = avatar.substring(avatar.indexOf("id=") + 3);
            return "http://localhost:8080/tutorsFinder/drive/view/" + id;
        }

        // Nếu đã là URL backend hoặc URL đúng → giữ nguyên
        return avatar;
    }

    default String toPreviewUrl(String url) {
        if (url == null)
            return null;

        // Link dạng uc?id=
        if (url.contains("id=")) {
            String id = url.substring(url.indexOf("id=") + 3);
            return "https://drive.google.com/file/d/" + id + "/preview";
        }

        // Link dạng file/d/<id>/view
        if (url.contains("/file/d/")) {
            String id = url.split("/file/d/")[1].split("/")[0];
            return "https://drive.google.com/file/d/" + id + "/preview";
        }

        // Link dạng open?id=
        if (url.contains("open?id=")) {
            String id = url.substring(url.indexOf("open?id=") + 8);
            return "https://drive.google.com/file/d/" + id + "/preview";
        }

        return url;
    }
}
