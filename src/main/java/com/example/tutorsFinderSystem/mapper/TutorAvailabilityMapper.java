package com.example.tutorsFinderSystem.mapper;

import com.example.tutorsFinderSystem.dto.response.TutorAvailabilityResponse;
import com.example.tutorsFinderSystem.entities.TutorAvailability;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TutorAvailabilityMapper {

    DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @Mapping(target = "availabilityId", source = "availabilityId")
    @Mapping(target = "dayOfWeek",
             expression = "java(availability.getStartTime().getDayOfWeek().name())")
    @Mapping(target = "startTime",
             expression = "java(availability.getStartTime().toLocalTime().format(TIME_FORMATTER))")
    @Mapping(target = "endTime",
             expression = "java(availability.getEndTime().toLocalTime().format(TIME_FORMATTER))")
    TutorAvailabilityResponse toResponse(TutorAvailability availability);

    List<TutorAvailabilityResponse> toResponses(List<TutorAvailability> availabilities);
}
