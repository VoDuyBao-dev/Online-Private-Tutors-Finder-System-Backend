package com.example.tutorsFinderSystem.mapper;

import com.example.tutorsFinderSystem.dto.response.tutorRequestClassResponse;
import com.example.tutorsFinderSystem.entities.CalendarClass;
import com.example.tutorsFinderSystem.entities.ClassEntity;
import com.example.tutorsFinderSystem.entities.ClassRequest;
import org.mapstruct.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TutorClassRequestMapper  {
    @Mapping(target = "requestId", source = "request.requestId")
    @Mapping(target = "classId", source = "classEntity.classId")

    @Mapping(target = "learnerId", source = "request.learner.learnerId")
    @Mapping(target = "fullName", source = "request.learner.fullName")
    @Mapping(target = "grade", source = "request.learner.grade")
    @Mapping(target = "school", source = "request.learner.school")

    @Mapping(target = "subjectId", source = "request.subject.subjectId")
    @Mapping(target = "subjectName", source = "request.subject.subjectName")

    @Mapping(target = "totalSessions", source = "request.totalSessions")
    @Mapping(target = "sessionsPerWeek", source = "request.sessionsPerWeek")
    @Mapping(target = "startDate", source = "request.startDate")
    @Mapping(target = "endDate", source = "request.endDate")
    @Mapping(target = "additionalNotes", source = "request.additionalNotes")

    @Mapping(target = "pricePerHour", source = "request.tutor.pricePerHour")

    @Mapping(target = "status", source = "request.status")
    @Mapping(target = "type", source = "request.type")
    @Mapping(target = "classStatus", source = "classEntity.status")

    @Mapping(target = "createdAt", source = "request.createdAt")

    @Mapping(target = "scheduleDescription", expression = "java(buildScheduleDescription(calendar))")
    tutorRequestClassResponse toSummary(ClassRequest request, ClassEntity classEntity, List<CalendarClass> calendar);

    default String buildScheduleDescription(List<CalendarClass> slots) {
        if (slots == null || slots.isEmpty())
            return null;

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return slots.stream()
                .map(s -> toVnDayOfWeek(s.getDayOfWeek())
                        + " - "
                        + s.getStartTime().format(timeFormatter)
                        + "-"
                        + s.getEndTime().format(timeFormatter))
                .collect(Collectors.joining("; "));
    }

    default String toVnDayOfWeek(com.example.tutorsFinderSystem.enums.DayOfWeek day) {
        if (day == null)
            return null;
        switch (day) {
            case MONDAY:
                return "Thứ 2";
            case TUESDAY:
                return "Thứ 3";
            case WEDNESDAY:
                return "Thứ 4";
            case THURSDAY:
                return "Thứ 5";
            case FRIDAY:
                return "Thứ 6";
            case SATURDAY:
                return "Thứ 7";
            case SUNDAY:
                return "Chủ nhật";
            default:
                return day.name();
        }
    }

}
