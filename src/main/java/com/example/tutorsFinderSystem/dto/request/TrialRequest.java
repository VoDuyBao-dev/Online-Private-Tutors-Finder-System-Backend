package com.example.tutorsFinderSystem.dto.request;

import com.example.tutorsFinderSystem.enums.DayOfWeek;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrialRequest {
    @NotNull(message = "TUTOR_ID_REQUIRE")
    private Long tutorId;

    @NotNull(message = "DAY_OF_WEEK_REQUIRED")
    private DayOfWeek dayOfWeek;

    @NotNull(message = "SUBJECT_ID_REQUIRED")
    private Long subjectId;

    @NotNull(message = "TRIAL_DATE_REQUIRE")
    @Future(message = "TRIAL_DATE_FUTURE")
    private LocalDate trialDate;

    @NotNull(message = "START_TIME_REQUIRED")
    private LocalTime startTime;

    @NotNull(message = "END_TIME_REQUIRED")
    private LocalTime endTime;

    @Size(max = 500, message = "ADDITIONAL_NOTES_TOO_LONG")
    private String additionalNotes;
}
