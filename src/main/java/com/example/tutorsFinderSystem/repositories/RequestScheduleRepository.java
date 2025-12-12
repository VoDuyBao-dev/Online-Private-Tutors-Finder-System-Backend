package com.example.tutorsFinderSystem.repositories;

import com.example.tutorsFinderSystem.entities.ClassRequest;
import com.example.tutorsFinderSystem.entities.Learner;
import com.example.tutorsFinderSystem.entities.RequestSchedule;
import com.example.tutorsFinderSystem.enums.DayOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface RequestScheduleRepository extends JpaRepository<RequestSchedule, Long> {
    List<RequestSchedule> findByClassRequest(ClassRequest classRequest);

    void deleteByClassRequest(ClassRequest classRequest);

    @Query("""
SELECT COUNT(rs) > 0
FROM RequestSchedule rs
JOIN ClassRequest cr ON rs.classRequest = cr
JOIN ClassEntity ce ON ce.classRequest = cr
WHERE cr.learner = :learner
  AND rs.dayOfWeek = :dayOfWeek
  AND :date BETWEEN cr.startDate AND cr.endDate
  AND rs.startTime < :endTime
  AND rs.endTime > :startTime
  AND (
        cr.status = com.example.tutorsFinderSystem.enums.ClassRequestStatus.PENDING
        OR (
            cr.status = com.example.tutorsFinderSystem.enums.ClassRequestStatus.CONFIRMED
            AND ce.status <> com.example.tutorsFinderSystem.enums.ClassStatus.COMPLETED
        )
      )
""")
    boolean hasLearnerTrialConflict(
            @Param("learner") Learner learner,
            @Param("dayOfWeek") DayOfWeek dayOfWeek,
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );

    List<RequestSchedule> findByClassRequest_RequestId(Long requestId);



}
