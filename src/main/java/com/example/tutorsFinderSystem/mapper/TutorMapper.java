package com.example.tutorsFinderSystem.mapper;

import com.example.tutorsFinderSystem.dto.response.TutorRegisterResponse;
import com.example.tutorsFinderSystem.entities.Subject;
import com.example.tutorsFinderSystem.entities.Tutor;
import com.example.tutorsFinderSystem.entities.TutorCertificate;

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

    // subjects (Set<Subject> → List<String>)
    @Mapping(target = "subjects", source = "subjects")

    // certificates (List<TutorCertificate> → List<String>)
    @Mapping(target = "certificates", source = "certificates")

    // verificationStatus enum → String
    @Mapping(
            target = "verificationStatus",
            expression = "java(tutor.getVerificationStatus() != null ? tutor.getVerificationStatus().name() : null)"
    )
    TutorRegisterResponse toTutorResponse(Tutor tutor);

    // Map subjects → subject names
    default List<String> mapSubjects(Set<Subject> subjects) {
        if (subjects == null || subjects.isEmpty()) return List.of();
        return subjects.stream()
                .map(Subject::getSubjectName)
                .collect(Collectors.toList());
    }

    // Map certificates → certificate names
    default List<String> mapCertificates(List<TutorCertificate> certificates) {
        if (certificates == null || certificates.isEmpty()) return List.of();
        return certificates.stream()
                .map(TutorCertificate::getFileUrl)
                .collect(Collectors.toList());
    }
}
