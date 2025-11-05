package com.example.tutorsFinderSystem.entities;

import com.example.tutorsFinderSystem.enums.ClassRequestStatus;
import com.example.tutorsFinderSystem.enums.ClassRequestType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "class_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long requestId;

    @ManyToOne
    @JoinColumn(name = "learner_id", nullable = false)
    private Learner learner; // người gửi yêu cầu (phụ huynh/học viên)

    @ManyToOne
    @JoinColumn(name = "tutor_id", nullable = false)
    private Tutor tutor; // người nhận yêu cầu

    @ManyToOne
    @JoinColumn(name = "subject", nullable = false)
    private Subject subject; // môn học yêu cầu dạy

    @Column(name = "total_sessions")
    private Integer totalSessions; // tổng số buổi học dự kiến

    @Column(name = "sessions_per_week", nullable = false)
    private Integer sessionsPerWeek; // số buổi học mỗi tuần

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "additional_notes", columnDefinition = "TEXT")
    private String additionalNotes;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private ClassRequestStatus status = ClassRequestStatus.PENDING;
    // PENDING, CONFIRMED, CANCELLED

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 20)
    private ClassRequestType type; // TRIAL hoặc OFFICIAL

    @Builder.Default
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
}
