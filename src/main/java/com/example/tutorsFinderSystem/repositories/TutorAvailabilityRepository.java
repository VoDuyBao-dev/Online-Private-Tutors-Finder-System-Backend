package com.example.tutorsFinderSystem.repositories;

import com.example.tutorsFinderSystem.entities.TutorAvailability;
import org.springframework.data.jpa.repository.*;
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

}
