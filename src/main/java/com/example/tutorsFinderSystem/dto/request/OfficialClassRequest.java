package com.example.tutorsFinderSystem.dto.request;

import com.example.tutorsFinderSystem.dto.common.WeeklyScheduleDTO;
import com.example.tutorsFinderSystem.enums.DayOfWeek;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfficialClassRequest {

    @NotNull(message = "TUTOR_ID_REQUIRED")
    private Long tutorId;

    @NotNull(message = "SUBJECT_ID_REQUIRED")
    private Long subjectId;

    @NotNull(message = "START_DATE_REQUIRED")
    @Future(message = "START_DATE_MUST_BE_FUTURE")
    private LocalDate startDate;

    @NotNull(message = "END_DATE_REQUIRED")
    private LocalDate endDate;

    @NotEmpty(message = "SCHEDULE_REQUIRED")
    @Size(min = 1, max = 3, message = "SCHEDULE_SIZE_INVALID")
    @Valid
    private List<WeeklyScheduleDTO> schedules;

    @Size(max = 500, message = "ADDITIONAL_NOTES_TOO_LONG")
    private String additionalNotes;





}