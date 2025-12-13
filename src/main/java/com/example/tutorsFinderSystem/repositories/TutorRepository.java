package com.example.tutorsFinderSystem.repositories;

import com.example.tutorsFinderSystem.entities.Tutor;
import com.example.tutorsFinderSystem.enums.UserStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TutorRepository extends JpaRepository<Tutor, Long> {

    Optional<Tutor> findByUserUserId(Long userId);

    Optional<Tutor> findByUser_Email(String email);

    // ===================== ROLE =====================

    @Query("""
            SELECT t
            FROM Tutor t
            JOIN t.user u
            WHERE :role MEMBER OF u.roles
            """)
    List<Tutor> findAllTutors(@Param("role") String role);

    @Query("""
            SELECT t
            FROM Tutor t
            JOIN t.user u
            WHERE :role MEMBER OF u.roles
            """)
    Page<Tutor> findAllTutorsPageable(
            @Param("role") String role,
            Pageable pageable
    );

    // ===================== ROLE + STATUS =====================

    @Query("""
            SELECT t
            FROM Tutor t
            JOIN t.user u
            WHERE :role MEMBER OF u.roles
              AND u.status = :status
            """)
    List<Tutor> findByUserStatus(
            @Param("role") String role,
            @Param("status") UserStatus status
    );

    // ===================== PENDING TUTORS =====================

    @Query("""
            SELECT t
            FROM Tutor t
            WHERE t.verificationStatus = com.example.tutorsFinderSystem.enums.TutorStatus.PENDING
            """)
    Page<Tutor> findPendingTutors(Pageable pageable);

    @Query(value = """
            SELECT COUNT(*)
            FROM tutors t
            WHERE t.verification_status = 'PENDING'
            """, nativeQuery = true)
    long countPendingTutors();
}
