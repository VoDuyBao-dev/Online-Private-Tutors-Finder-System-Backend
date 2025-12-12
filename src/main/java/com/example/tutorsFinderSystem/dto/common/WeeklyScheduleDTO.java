package com.example.tutorsFinderSystem.dto.common;

import com.example.tutorsFinderSystem.enums.DayOfWeek;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeeklyScheduleDTO {

    @NotNull(message = "DAY_OF_WEEK_REQUIRED")
    private DayOfWeek dayOfWeek;

    @NotNull(message = "START_TIME_REQUIRED")
    private LocalTime startTime;

    @NotNull(message = "END_TIME_REQUIRED")
    private LocalTime endTime;
}
