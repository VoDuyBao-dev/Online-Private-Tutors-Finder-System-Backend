package com.example.tutorsFinderSystem.mapper;

import com.example.tutorsFinderSystem.dto.response.LearnerCalendarResponse;
import com.example.tutorsFinderSystem.entities.CalendarClass;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.LocalTime;

@Mapper(componentModel = "spring")
public interface CalendarClassMapper {

    @Mapping(target = "date", source = "studyDate")
    @Mapping(target = "session", expression = "java(resolveSession(entity.getStartTime()))")
    @Mapping(target = "subjectName", source = "classRequest.subject.subjectName")
    @Mapping(target = "tutorName", source = "classRequest.tutor.user.fullName")
    LearnerCalendarResponse toResponse(CalendarClass entity);

    default String resolveSession(LocalTime start) {
        if (start.isBefore(LocalTime.NOON)) return "Sáng";
        if (start.isBefore(LocalTime.of(18, 0))) return "Chiều";
        return "Tối";
    }
}
