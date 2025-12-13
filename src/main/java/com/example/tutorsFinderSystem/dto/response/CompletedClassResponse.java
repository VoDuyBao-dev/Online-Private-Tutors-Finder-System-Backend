package com.example.tutorsFinderSystem.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CompletedClassResponse {
    private Long classId;

    private Long tutorId;
    private String tutorName;

    private Long subjectId;
    private String subjectName;

    private LocalDate startDate;
    private LocalDate endDate;

    private boolean canRate;
}
