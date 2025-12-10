package com.example.tutorsFinderSystem.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TutorRequestClassResponse {

    private Long requestId;
    private Long classId;

    private Long learnerId;
    private String fullName; // từ learner.full_name
    private String grade; // từ learner.grade
    private String school; // từ learner.school

    private Long subjectId; // subject → subject_id
    private String subjectName; // subject_name

    private Integer totalSessions; // total_sessions
    private Integer sessionsPerWeek; // sessions_per_week
    private LocalDate startDate; // start_date
    private LocalDate endDate; // end_date
    private String additionalNotes; // additional_notes

    private Integer pricePerHour; // price_per_hour (tutors)

    private String status; // class_requests.status
    private String type; // class_requests.type
    private String classStatus; // classes.status

    private LocalDateTime createdAt; // class_requests.created_at

    // Không có trong DB nhưng cần:
    // tạo field mô tả lịch học cho frontend
    private String scheduleDescription;
}
