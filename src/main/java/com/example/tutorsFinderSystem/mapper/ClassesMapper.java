package com.example.tutorsFinderSystem.mapper;

import com.example.tutorsFinderSystem.dto.common.RelatedClassDTO;
import com.example.tutorsFinderSystem.dto.response.CompletedClassResponse;
import com.example.tutorsFinderSystem.entities.ClassEntity;
import com.example.tutorsFinderSystem.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ClassesMapper {
    @Mapping(target = "classId", source = "classEntity.classId")
    @Mapping(target = "tutorId", source = "classEntity.classRequest.tutor.tutorId")
    @Mapping(target = "avatarImage", expression = "java(getAvatar(classEntity))")
    @Mapping(target = "subjectName", source = "classEntity.classRequest.subject.subjectName")
    @Mapping(target = "teacherName", source = "classEntity.classRequest.tutor.user.fullName")
    @Mapping(target = "educationalLevel", source = "classEntity.classRequest.tutor.educationalLevel")
    @Mapping(target = "university", source = "classEntity.classRequest.tutor.university")
    @Mapping(target = "pricePerHour", source = "classEntity.classRequest.tutor.pricePerHour")
    @Mapping(target = "introduction", source = "classEntity.classRequest.tutor.introduction")
    RelatedClassDTO toRelatedClassDTO(ClassEntity classEntity);

    // Custom xử lý avatar rỗng
    default String getAvatar(ClassEntity c) {
        User user = c.getClassRequest().getTutor().getUser();
        return user.getAvatarImage() != null ? user.getAvatarImage() : "";
    }

    @Mappings({
            @Mapping(target = "classId", source = "classEntity.classId"),
            @Mapping(target = "tutorId", source = "classEntity.classRequest.tutor.tutorId"),
            @Mapping(target = "tutorName", source = "classEntity.classRequest.tutor.user.fullName"),
            @Mapping(target = "subjectId", source = "classEntity.classRequest.subject.subjectId"),
            @Mapping(target = "subjectName", source = "classEntity.classRequest.subject.subjectName"),
            @Mapping(target = "startDate", source = "classEntity.classRequest.startDate"),
            @Mapping(target = "endDate", source = "classEntity.classRequest.endDate"),
            @Mapping(target = "canRate", source = "canRate")
    })
    CompletedClassResponse toCompletedClassResponse(ClassEntity classEntity, boolean canRate);
}
