package com.example.tutorsFinderSystem.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.tutorsFinderSystem.entities.Learner;

public interface LearnerRepository extends JpaRepository<Learner, Long> {
    @Query("""
                SELECT t
                FROM Learner t
                JOIN t.user u
                WHERE u.role = com.example.tutorsFinderSystem.enums.Role.LEARNER
            """)
    Page<Learner> findAllLearnerPageable(Pageable pageable);

    @Query("""
                SELECT COUNT(u)
                FROM User u
                WHERE u.role = com.example.tutorsFinderSystem.enums.Role.LEARNER
            """)
    long countAllLearners();

    @Query("""
                SELECT COUNT(u)
                FROM User u
                WHERE u.role = com.example.tutorsFinderSystem.enums.Role.LEARNER
                  AND u.status = com.example.tutorsFinderSystem.enums.UserStatus.ACTIVE
            """)
    long countActiveLearners();

    @Query("""
                SELECT COUNT(u)
                FROM User u
                WHERE u.role = com.example.tutorsFinderSystem.enums.Role.LEARNER
                  AND u.status = com.example.tutorsFinderSystem.enums.UserStatus.INACTIVE
            """)
    long countInactiveLearners();

}
