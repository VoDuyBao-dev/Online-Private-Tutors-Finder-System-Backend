package com.example.tutorsFinderSystem.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.tutorsFinderSystem.enums.ClassStatus;
import com.example.tutorsFinderSystem.enums.DayOfWeek;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TutorTeachingScheduleResponse {

    private LocalDate studyDate;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    private String subjectName;
    private String learnerName;
    private ClassStatus classStatus;
}
