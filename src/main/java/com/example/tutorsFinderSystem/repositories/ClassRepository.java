package com.example.tutorsFinderSystem.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

  // Lấy danh sách lớp học liên quan (cùng môn học hoặc cùng gia sư)
  @Query("""
        SELECT c FROM ClassEntity c 
          JOIN c.classRequest r 
          JOIN r.tutor t 
          JOIN r.subject s 
          WHERE (s.subjectId = :subjectId OR t.tutorId = :tutorId) 
          AND c.classId != :excludeClassId 
          AND c.status IN ('ONGOING', 'PENDING') 
          ORDER BY c.classId DESC
                  """)
  List<ClassEntity> findRelatedClasses(
          @Param("subjectId") Long subjectId,
          @Param("tutorId") Long tutorId,
          @Param("excludeClassId") Long excludeClassId,
          Pageable pageable
  );

  // Lấy tất cả lớp học đang hoạt động
  @Query("""
    SELECT c FROM ClassEntity c 
          WHERE c.status IN ('ONGOING', 'PENDING') 
          ORDER BY c.classId DESC
              """)
  Page<ClassEntity> findAllActiveClasses(Pageable pageable);

}
