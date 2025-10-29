package com.example.tutorsFinderSystem.entities;

import com.example.tutorsFinderSystem.enums.ClassStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "classes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private Long classId;

    @OneToOne
    @JoinColumn(name = "request_id")
    private ClassRequest classRequest;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private ClassStatus status = ClassStatus.PENDING;
    // PENDING, ONGOING, COMPLETED, CANCELLED

    @Column(name = "completed_sessions")
    private Integer completedSessions = 0;
}
