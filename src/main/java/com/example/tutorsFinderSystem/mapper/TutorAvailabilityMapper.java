package com.example.tutorsFinderSystem.mapper;

import com.example.tutorsFinderSystem.dto.response.TutorAvailabilityResponse;
import com.example.tutorsFinderSystem.entities.TutorAvailability;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface TutorAvailabilityMapper {

    DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
    DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Mapping(target = "availabilityId", source = "availability.availabilityId")
    @Mapping(target = "status", source = "availability.status")

    @Mapping(
        target = "dayOfWeek",
        expression = "java(availability.getStartTime().getDayOfWeek().name())"
    )
    @Mapping(
        target = "startTime",
        expression = "java(availability.getStartTime().toLocalTime().format(TIME_FORMAT))"
    )
    @Mapping(
        target = "endTime",
        expression = "java(availability.getEndTime().toLocalTime().format(TIME_FORMAT))"
    )
    @Mapping(
        target = "startDate",
        expression = "java(availability.getStartTime().toLocalDate().format(DATE_FORMAT))"
    )
    // endDate KHÔNG map ở đây
    @Mapping(target = "endDate", ignore = true)
    TutorAvailabilityResponse toResponse(TutorAvailability availability);
}
