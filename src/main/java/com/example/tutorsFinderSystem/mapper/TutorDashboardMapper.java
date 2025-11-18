package com.example.tutorsFinderSystem.mapper;

import com.example.tutorsFinderSystem.dto.response.TutorDashboardResponse;
import com.example.tutorsFinderSystem.entities.ClassRequest;
import com.example.tutorsFinderSystem.entities.Subject;
import com.example.tutorsFinderSystem.entities.Tutor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TutorDashboardMapper {

    // Tutor -> TutorInfo
    @Mapping(source = "user.fullName", target = "fullName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.phoneNumber", target = "phoneNumber")
    @Mapping(source = "user.avatarImage", target = "avatarUrl")
    @Mapping(source = "subjects", target = "subjects") 
    TutorDashboardResponse.TutorInfo toTutorInfo(Tutor tutor);

    // ClassRequest -> ClassItem
    @Mapping(source = "requestId", target = "classId")
    @Mapping(source = "learner.fullName", target = "learnerName")
    @Mapping(source = "learner.address", target = "learnerAddress")
    @Mapping(source = "subject.subjectName", target = "subjectName")
    @Mapping(expression = "java(cr.getStartDate().toString())", target = "startDate")
    @Mapping(expression = "java(cr.getEndDate().toString())", target = "endDate")
    TutorDashboardResponse.ClassItem toClassItem(ClassRequest cr);

    // Custom: Set<Subject> -> List<String>
    default java.util.List<String> mapSubjects(java.util.Set<Subject> subjects) {
        return subjects.stream()
                .map(Subject::getSubjectName)
                .toList();
    }
}
