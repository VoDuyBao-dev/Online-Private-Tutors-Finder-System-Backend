package com.example.tutorsFinderSystem.entities;

import com.example.tutorsFinderSystem.enums.Gender;
import com.example.tutorsFinderSystem.enums.TutorStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tutors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tutor_id")
    private Long tutorId;

    // Liên kết 1-1 với User (khóa ngoại user_id)
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 10)
    private Gender gender;

    @Column(name = "address", nullable = false, length = 255)
    private String address;

    @Column(name = "university", nullable = false, length = 255)
    private String university;

    @Column(name = "proof_file_url", nullable = false, length = 500)
    private String proofFileUrl;

    @Column(name = "introduction", columnDefinition = "TEXT")
    private String introduction;

    @Column(name = "price_per_hour", nullable = false)
    private Integer pricePerHour;

    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status", length = 20)
    private TutorStatus verificationStatus = TutorStatus.PENDING;

    // Một gia sư có thể dạy nhiều môn học
    @ManyToMany
    @JoinTable(
            name = "tutor_subjects",
            joinColumns = @JoinColumn(name = "tutor_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private Set<Subject> subjects = new HashSet<>();
    
}
