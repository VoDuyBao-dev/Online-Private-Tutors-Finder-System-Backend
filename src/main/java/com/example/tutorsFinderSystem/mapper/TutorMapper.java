package com.example.tutorsFinderSystem.mapper;

import com.example.tutorsFinderSystem.dto.response.TutorRegisterResponse;
import com.example.tutorsFinderSystem.entities.Subject;
import com.example.tutorsFinderSystem.entities.Tutor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring") // ✅ rất quan trọng
public interface TutorMapper {

    @Mapping(target = "subjects", source = "subjects")
    @Mapping(target = "fullName", expression = "java(tutor.getUser().getFullName())")
    @Mapping(target = "email", expression = "java(tutor.getUser().getEmail())")
    @Mapping(target = "phoneNumber", expression = "java(tutor.getUser().getPhoneNumber())")
    @Mapping(target = "avatarUrl", expression = "java(tutor.getUser().getAvatarImage())")
    TutorRegisterResponse toTutorResponse(Tutor tutor);

    default List<String> mapSubjects(Set<Subject> subjects) {
        if (subjects == null) return List.of();
        return subjects.stream()
                .map(Subject::getSubjectName)
                .collect(Collectors.toList());
    }
}
