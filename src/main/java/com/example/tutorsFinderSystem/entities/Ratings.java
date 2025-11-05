package com.example.tutorsFinderSystem.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ratings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ratings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id")
    private Long ratingId;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private ClassEntity classEntity;  // Lớp học được đánh giá

    @Column(name = "score", nullable = false)
    private Float score;  // điểm số 1–5

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment; // nội dung đánh giá (có thể null)

    @Builder.Default
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
