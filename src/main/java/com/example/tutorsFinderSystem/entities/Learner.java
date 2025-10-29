package com.example.tutorsFinderSystem.entities;

import com.example.tutorsFinderSystem.enums.Gender;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "learner")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Learner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "learner_id")
    private Long learnerId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // liên kết 1-1 với User

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 10)
    private Gender gender;

    @Column(name = "grade", length = 20)
    private String grade; // lớp học (VD: lớp 10, lớp 12)

    @Column(name = "school", length = 255)
    private String school;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "address", nullable = false, length = 255)
    private String address; // Thành phố / Quận / Phường

    @Column(name = "address_detail", length = 255)
    private String addressDetail; // Số nhà, hẻm, thôn...

}
