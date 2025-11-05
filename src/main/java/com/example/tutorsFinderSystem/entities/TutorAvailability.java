package com.example.tutorsFinderSystem.entities;

import com.example.tutorsFinderSystem.enums.TutorAvailabilityStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tutor_availability")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TutorAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "availability_id")
    private Long availabilityId;

    @ManyToOne
    @JoinColumn(name = "tutor_id", nullable = false)
    private Tutor tutor;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime; // ngày + giờ bắt đầu

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;   // ngày + giờ kết thúc

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private TutorAvailabilityStatus status = TutorAvailabilityStatus.AVAILABLE; 
    // AVAILABLE, BOOKED, CANCELLED

    @Builder.Default
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
}
