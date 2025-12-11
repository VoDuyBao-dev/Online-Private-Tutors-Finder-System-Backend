package com.example.tutorsFinderSystem.repositories;

import com.example.tutorsFinderSystem.entities.ClassRequest;
import com.example.tutorsFinderSystem.enums.ClassRequestStatus;
import com.example.tutorsFinderSystem.enums.ClassRequestType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRequestRepository extends JpaRepository<ClassRequest, Long> {

    List<ClassRequest> findAll();

    @Query("""
        SELECT COUNT(cr)
        FROM ClassRequest cr
        WHERE cr.tutor.tutorId = :tutorId
          AND cr.status = 'PENDING'
    """)
    int countNewRequests(Long tutorId);

    

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

    Page<ClassRequest> findByLearner_User_Email(String email, Pageable pageable);



}
