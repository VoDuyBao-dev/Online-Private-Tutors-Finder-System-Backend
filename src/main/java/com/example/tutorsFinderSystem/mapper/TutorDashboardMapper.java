package com.example.tutorsFinderSystem.mapper;

import com.example.tutorsFinderSystem.dto.response.TutorDashboardResponse;
import com.example.tutorsFinderSystem.entities.ClassEntity;
import com.example.tutorsFinderSystem.entities.Subject;
import com.example.tutorsFinderSystem.entities.Tutor;
import com.example.tutorsFinderSystem.entities.TutorCertificate;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TutorDashboardMapper {

    @Mapping(source = "user.fullName", target = "fullName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.phoneNumber", target = "phoneNumber")
    @Mapping(source = "user.avatarImage", target = "avatarUrl")
    @Mapping(source = "subjects", target = "subjects")
    TutorDashboardResponse.TutorInfo toTutorInfo(Tutor tutor);

    @Mapping(source = "classId", target = "classId")
    @Mapping(source = "classRequest.learner.fullName", target = "learnerName")
    @Mapping(source = "classRequest.learner.address", target = "learnerAddress")
    @Mapping(source = "classRequest.subject.subjectName", target = "subjectName")
    @Mapping(expression = "java(c.getClassRequest().getStartDate().toString())", target = "startDate")
    @Mapping(expression = "java(c.getClassRequest().getEndDate().toString())", target = "endDate")
    @Mapping(source = "status", target = "status")
    TutorDashboardResponse.ClassItem toClassItem(ClassEntity c);

    // map subjects
    default java.util.List<String> mapSubjects(java.util.Set<Subject> subjects) {
        if (subjects == null) return java.util.List.of();
        return subjects.stream()
                .map(Subject::getSubjectName)
                .toList();
    }
    default List<String> mapCertificates(List<TutorCertificate> certificates) {
        if (certificates == null || certificates.isEmpty()) return List.of();
        return certificates.stream()
                .map(TutorCertificate::getCertificateName)
                .collect(Collectors.toList());
    }
}
