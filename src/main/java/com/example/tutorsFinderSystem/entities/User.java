package com.example.tutorsFinderSystem.entities;

import com.example.tutorsFinderSystem.enums.Role;
import com.example.tutorsFinderSystem.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private Role role; // ADMIN, TUTOR, PARENT

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "avatar_image", length = 255)
    private String avatarImage;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private UserStatus status = UserStatus.ACTIVE; // ACTIVE, INACTIVE, LOCKED

    @Builder.Default
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Relationships --------------------

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Tutor tutorProfile; // Gia sư (nếu là role = TUTOR)

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Learner learnerProfile; // Học viên/Phụ huynh (nếu là role = PARENT)

}
