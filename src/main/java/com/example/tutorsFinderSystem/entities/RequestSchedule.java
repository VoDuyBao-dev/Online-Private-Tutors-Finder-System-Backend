package com.example.tutorsFinderSystem.entities;

import com.example.tutorsFinderSystem.enums.DayOfWeek;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "request_schedules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_schedules_id")
    private Long requestSchedulesId;

    @ManyToOne
    @JoinColumn(name = "request_id", nullable = false)
    private ClassRequest classRequest;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false, length = 20)
    private DayOfWeek dayOfWeek;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();


}
