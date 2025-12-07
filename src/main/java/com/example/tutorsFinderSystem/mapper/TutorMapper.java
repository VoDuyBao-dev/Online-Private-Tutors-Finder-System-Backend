package com.example.tutorsFinderSystem.mapper;

import com.example.tutorsFinderSystem.dto.response.TutorRegisterResponse;
import com.example.tutorsFinderSystem.entities.Subject;
import com.example.tutorsFinderSystem.entities.Tutor;
import com.example.tutorsFinderSystem.entities.TutorCertificate;
import com.example.tutorsFinderSystem.entities.TutorCertificateFile;

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
}
