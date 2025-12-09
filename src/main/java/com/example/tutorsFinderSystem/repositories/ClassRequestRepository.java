package com.example.tutorsFinderSystem.repositories;

import com.example.tutorsFinderSystem.entities.ClassRequest;
import com.example.tutorsFinderSystem.enums.ClassRequestStatus;
import com.example.tutorsFinderSystem.enums.ClassRequestType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRequestRepository extends JpaRepository<ClassRequest, Long> {

    // @Query("""
    //     SELECT cr 
    //     FROM ClassRequest cr
    //     JOIN FETCH cr.learner l
    //     JOIN FETCH cr.subject s
    //     WHERE cr.tutor.tutorId = :tutorId 
    //       AND cr.status = 'CONFIRMED'
    // """)
    // Page<ClassRequest> findActiveClasses(Long tutorId, Pageable pageable);

    @Query("""
        SELECT COUNT(cr)
        FROM ClassRequest cr
        WHERE cr.tutor.tutorId = :tutorId
          AND cr.status = 'PENDING'
    """)
    int countNewRequests(Long tutorId);

    // @Query("""
    //     SELECT COUNT(cr)
    //     FROM ClassRequest cr
    //     WHERE cr.tutor.tutorId = :tutorId
    //       AND cr.status = 'CONFIRMED'
    // """)
    // int countActiveClasses(Long tutorId);

    Page<ClassRequest> findByTutor_TutorId(Long tutorId, Pageable pageable);

    Page<ClassRequest> findByTutor_TutorIdAndStatus(
            Long tutorId,
            ClassRequestStatus status,
            Pageable pageable
    );

    Page<ClassRequest> findByTutor_TutorIdAndType(
            Long tutorId,
            ClassRequestType type,
            Pageable pageable
    );

    Page<ClassRequest> findByTutor_TutorIdAndStatusAndType(
            Long tutorId,
            ClassRequestStatus status,
            ClassRequestType type,
            Pageable pageable
    );


}
