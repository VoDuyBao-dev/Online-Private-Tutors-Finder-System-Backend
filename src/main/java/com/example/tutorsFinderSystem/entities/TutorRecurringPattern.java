package com.example.tutorsFinderSystem.entities;

import com.example.tutorsFinderSystem.enums.RepeatType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "tutor_recurring_pattern")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TutorRecurringPattern {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pattern_id")
    private Long patternId;

    @ManyToOne
    @JoinColumn(name = "tutor_id", nullable = false)
    private Tutor tutor;

    @Enumerated(EnumType.STRING)
    @Column(name = "repeat_type", nullable = false, length = 20)
    private RepeatType repeatType; // WEEKLY hoặc MONTHLY

    @Column(name = "days_of_week", columnDefinition = "JSON")
    private String daysOfWeek;  

    @Column(name = "days_of_month", columnDefinition = "JSON")
    private String daysOfMonth; 

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "repeat_start", nullable = false)
    private LocalDate repeatStart;

    @Column(name = "repeat_end", nullable = false)
    private LocalDate repeatEnd;
}
