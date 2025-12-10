package com.example.tutorsFinderSystem.repositories;

import java.util.Optional;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.tutorsFinderSystem.entities.ClassEntity;

@Repository
public interface ClassRepository extends JpaRepository<ClassEntity, Long> {
  @Query("""
          SELECT c
          FROM ClassEntity c
          JOIN FETCH c.classRequest cr
          JOIN FETCH cr.learner l
          JOIN FETCH cr.subject s
          WHERE cr.tutor.tutorId = :tutorId
            AND c.status = 'ONGOING'
      """)
  Page<ClassEntity> findOngoingClasses(Long tutorId, Pageable pageable);

  @Query("""
          SELECT COUNT(c)
          FROM ClassEntity c
          WHERE c.classRequest.tutor.tutorId = :tutorId
            AND c.status = 'ONGOING'
      """)
  int countOngoingClasses(Long tutorId);

  Optional<ClassEntity> findByClassRequest_RequestId(Long requestId);

}
