package com.example.tutorsFinderSystem.repositories;

import com.example.tutorsFinderSystem.entities.Learner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LearnerRepository extends JpaRepository<Learner, Long> {

    // ===================== ROLE =====================

    @Query("""
            SELECT l
            FROM Learner l
            JOIN l.user u
            WHERE :role MEMBER OF u.roles
            """)
    Page<Learner> findAllLearnerPageable(
            @Param("role") String role,
            Pageable pageable);

    // ===================== COUNT =====================

    @Query("""
            SELECT COUNT(l)
            FROM Learner l
            JOIN l.user u
            WHERE :role MEMBER OF u.roles
            """)
    long countAllLearners(@Param("role") String role);

    @Query("""
            SELECT COUNT(l)
            FROM Learner l
            JOIN l.user u
            WHERE :role MEMBER OF u.roles
              AND u.status = com.example.tutorsFinderSystem.enums.UserStatus.ACTIVE
            """)
    long countActiveLearners(@Param("role") String role);

    @Query("""
            SELECT COUNT(l)
            FROM Learner l
            JOIN l.user u
            WHERE :role MEMBER OF u.roles
              AND u.status = com.example.tutorsFinderSystem.enums.UserStatus.INACTIVE
            """)
    long countInactiveLearners(@Param("role") String role);

    // ===================== FIND =====================

    Optional<Learner> findByUser_Email(String email);

    Optional<Learner> findByUserUserId(Long userId);
}
