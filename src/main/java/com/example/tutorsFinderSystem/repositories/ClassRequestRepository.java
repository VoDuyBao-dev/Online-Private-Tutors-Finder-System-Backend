package com.example.tutorsFinderSystem.repositories;

import com.example.tutorsFinderSystem.entities.ClassRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRequestRepository extends JpaRepository<ClassRequest, Long> {

    @Query("""
        SELECT cr 
        FROM ClassRequest cr
        JOIN FETCH cr.learner l
        JOIN FETCH cr.subject s
        WHERE cr.tutor.tutorId = :tutorId 
          AND cr.status = 'CONFIRMED'
    """)
    Page<ClassRequest> findActiveClasses(Long tutorId, Pageable pageable);

    @Query("""
        SELECT COUNT(cr)
        FROM ClassRequest cr
        WHERE cr.tutor.tutorId = :tutorId
          AND cr.status = 'PENDING'
    """)
    int countNewRequests(Long tutorId);
}
