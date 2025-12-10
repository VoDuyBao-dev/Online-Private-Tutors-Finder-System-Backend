package com.example.tutorsFinderSystem.mapper;

import com.example.tutorsFinderSystem.dto.common.*;
import com.example.tutorsFinderSystem.dto.response.TutorRegisterResponse;
import com.example.tutorsFinderSystem.entities.*;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TutorMapper {

    @Mapping(target = "tutorId", source = "tutorId")

    // USER
    @Mapping(target = "fullName", source = "user.fullName")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "phoneNumber", source = "user.phoneNumber")
    @Mapping(target = "avatarUrl", source = "user.avatarImage")

    // SUBJECTS
    @Mapping(target = "subjects", expression = "java(mapSubjects(tutor.getSubjects()))")

    // CERTIFICATES → Pending files only
    @Mapping(target = "certificates", expression = "java(getPendingFiles(tutor.getCertificates()))")

    // STATUS
    @Mapping(
            target = "verificationStatus",
            expression = "java(tutor.getVerificationStatus() != null ? tutor.getVerificationStatus().name() : null)"
    )
    TutorRegisterResponse toTutorResponse(Tutor tutor);

    // ----------------------
    // SUBJECTS MAPPING
    // ----------------------
    default List<String> mapSubjects(Set<Subject> subjects) {
        if (subjects == null || subjects.isEmpty()) return List.of();

        return subjects.stream()
                .map(Subject::getSubjectName)
                .collect(Collectors.toList());
    }


    // ----------------------
    // PENDING FILES
    // ----------------------
    default List<String> getPendingFiles(List<TutorCertificate> certificates) {
        if (certificates == null || certificates.isEmpty()) return List.of();

        return certificates.stream()
                .flatMap(cert ->
                        cert.getFiles().stream()
                                .filter(f -> f.getStatus().name().equals("PENDING"))
                                .map(TutorCertificateFile::getFileUrl)
                )
                .toList();
    }

    // ----------------------
    // APPROVED FILES
    // ----------------------
    default List<String> getApprovedFiles(List<TutorCertificate> certificates) {
        if (certificates == null || certificates.isEmpty()) return List.of();

        return certificates.stream()
                .flatMap(cert ->
                        cert.getFiles().stream()
                                .filter(f -> f.getStatus().name().equals("APPROVED"))
                                .map(TutorCertificateFile::getFileUrl)
                )
                .toList();
    }

    // ----------------------
    // REJECTED FILES
    // ----------------------
    default List<String> getRejectedFiles(List<TutorCertificate> certificates) {
        if (certificates == null || certificates.isEmpty()) return List.of();

        return certificates.stream()
                .flatMap(cert ->
                        cert.getFiles().stream()
                                .filter(f -> f.getStatus().name().equals("REJECTED"))
                                .map(TutorCertificateFile::getFileUrl)
                )
                .toList();
    }

//    Chi tieets thong tin gia su
    @Mapping(target = "tutorId", source = "tutor.tutorId")

    // USER
    @Mapping(target = "fullName", source = "tutor.user.fullName")
    @Mapping(target = "email", source = "tutor.user.email")
    @Mapping(target = "phoneNumber", source = "tutor.user.phoneNumber")
    @Mapping(target = "avatarImage", source = "tutor.user.avatarImage")

    // BASIC INFO
    @Mapping(target = "gender", expression = "java(tutor.getGender() != null ? tutor.getGender().name() : null)")
    @Mapping(target = "address", source = "tutor.address")
    @Mapping(target = "university", source = "tutor.university")
    @Mapping(target = "educationalLevel", source = "tutor.educationalLevel")
    @Mapping(target = "introduction", source = "tutor.introduction")
    @Mapping(target = "pricePerHour", source = "tutor.pricePerHour")
    @Mapping(target = "verificationStatus", source = "tutor.verificationStatus")
    @Mapping(target = "userStatus", source = "tutor.user.status")

    // SUBJECTS
    @Mapping(target = "subjects", expression = "java(mapSubjectDTOList(tutor.getSubjects()))")

    // CERTIFICATES → chỉ lấy approved
    @Mapping(target = "certificates", expression = "java(mapApprovedCertificates(tutor.getCertificates()))")

    // RATING
    @Mapping(target = "ratingStatistics", source = "ratingStatistics")
    @Mapping(target = "recentReviews", expression = "java(mapReviews(recentReviews))")
    TutorDetailDTO toTutorDetailDTO(
            Tutor tutor,
            RatingStatisticsDTO ratingStatistics,
            List<Ratings> recentReviews
    );


    // ----------------------
    //  CUSTOM MAPPINGS
    // ----------------------

    // SUBJECTS
    default List<SubjectDTO> mapSubjectDTOList(Set<Subject> subjects) {
        return subjects.stream()
                .map(s -> new SubjectDTO(s.getSubjectId(), s.getSubjectName()))
                .collect(Collectors.toList());
    }

    // CERTIFICATES APPROVED only
    default List<CertificateDTO> mapApprovedCertificates(List<TutorCertificate> certificates) {
        return certificates.stream()
                .filter(TutorCertificate::getApproved)
                .flatMap(cert ->
                        cert.getFiles().stream()
                                .filter(file -> file.getStatus() == com.example.tutorsFinderSystem.enums.CertificateStatus.APPROVED)
                                .map(file -> new CertificateDTO(
                                        cert.getCertificateId(),
                                        cert.getCertificateName(),
                                        file.getFileUrl(),
                                        file.getUploadedAt()
                                ))
                )
                .collect(Collectors.toList());
    }

    // RATING LIST
    default List<RatingDTO> mapReviews(List<Ratings> ratings) {
        return ratings.stream()
                .map(r -> RatingDTO.builder()
                        .ratingId(r.getRatingId())
                        .score(r.getScore())
                        .comment(r.getComment())
                        .createdAt(r.getCreatedAt())
                        .learnerName(r.getClassEntity().getClassRequest().getLearner().getFullName())
                        .learnerAvatar(r.getClassEntity().getClassRequest().getLearner().getUser().getAvatarImage())
                        .build()
                ).toList();
    }
}

