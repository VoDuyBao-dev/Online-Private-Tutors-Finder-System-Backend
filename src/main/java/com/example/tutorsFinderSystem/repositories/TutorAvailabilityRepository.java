package com.example.tutorsFinderSystem.repositories;

import com.example.tutorsFinderSystem.entities.TutorAvailability;
import com.example.tutorsFinderSystem.enums.TutorAvailabilityStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TutorAvailabilityRepository extends JpaRepository<TutorAvailability, Long> {

    @Query("""
                SELECT COUNT(a)
                FROM TutorAvailability a
                WHERE a.tutor.tutorId = :tutorId
            """)
    int countSchedules(Long tutorId);

    @Query("""
                SELECT COUNT(a)
                FROM TutorAvailability a
                WHERE a.tutor.tutorId = :tutorId
                  AND a.status = 'AVAILABLE'
                  AND FUNCTION('YEARWEEK', a.startTime, 1) = FUNCTION('YEARWEEK', CURRENT_DATE(), 1)
            """)
    int countWeeklySchedules(Long tutorId);

    List<TutorAvailability> findByTutor_TutorIdOrderByStartTimeAsc(Long tutorId);

    boolean existsByTutor_TutorIdAndStartTimeAndEndTimeAndStatus(
            Long tutorId,
            LocalDateTime startTime,
            LocalDateTime endTime,
            TutorAvailabilityStatus status);
    List<TutorAvailability> findByTutor_TutorIdOrderByStartTime(Long tutorId);

    boolean existsByTutor_TutorIdAndStartTimeAndEndTime(Long tutorId,
                                                         LocalDateTime startTime,
                                                         LocalDateTime endTime);

    @Query("""
        SELECT ta
        FROM TutorAvailability ta
        WHERE ta.tutor.tutorId = :tutorId
          AND ta.startTime >= :from
          AND ta.endTime <= :to
        ORDER BY ta.startTime
    """)
    List<TutorAvailability> findAvailableByTutorAndTimeRange(
            @Param("tutorId") Long tutorId,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );

    @Query("""
        SELECT ta
        FROM TutorAvailability ta
        WHERE ta.tutor.tutorId = :tutorId
          AND ta.startTime = :start
          AND ta.endTime = :end
          AND ta.status = 'AVAILABLE'
    """)
    Optional<TutorAvailability> findAvailableSlot(
            Long tutorId,
            LocalDateTime start,
            LocalDateTime end
    );

}
