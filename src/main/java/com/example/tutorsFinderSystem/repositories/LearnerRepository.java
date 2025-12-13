package com.example.tutorsFinderSystem.repositories;

import com.example.tutorsFinderSystem.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.tutorsFinderSystem.entities.Learner;
import com.example.tutorsFinderSystem.entities.Tutor;

import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LearnerRepository extends JpaRepository<Learner, Long> {
    @Query("""
                SELECT l
                FROM Learner l
                JOIN l.user u
                WHERE :role MEMBER OF u.roles
""")
    Page<Learner> findAllLearnerPageable(@Param("role") Role role, Pageable pageable);

    @Query("""
               SELECT COUNT(l)
                FROM Learner l
                JOIN l.user u
                WHERE :role MEMBER OF u.roles
            """)
    long countAllLearners(@Param("role") Role role);

    @Query("""
                SELECT COUNT(l)
               FROM Learner l
               JOIN l.user u
               WHERE :role MEMBER OF u.roles
                 AND u.status = com.example.tutorsFinderSystem.enums.UserStatus.ACTIVE
            """)
    long countActiveLearners(@Param("role") Role role);

    @Query("""
            SELECT COUNT(l)
            FROM Learner l
            JOIN l.user u
            WHERE :role MEMBER OF u.roles
            AND u.status = com.example.tutorsFinderSystem.enums.UserStatus.INACTIVE
            """)
    long countInactiveLearners(@Param("role") Role role);

    Optional<Learner> findByUser_Email(String email);

    Optional<Learner> findByUserUserId(Long userId);
}
