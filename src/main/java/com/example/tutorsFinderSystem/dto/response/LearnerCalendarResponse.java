package com.example.tutorsFinderSystem.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LearnerCalendarResponse {

    private Long calendarClassId;
    private LocalDate date;          // 2025-11-04
    private String dayOfWeek;          // 2025-11-04
    private LocalTime startTime;      // 08:00
    private LocalTime endTime;        // 09:30
    private String session;           // Sáng / Chiều / Tối
    private String subjectName;
    private String tutorName;

}
