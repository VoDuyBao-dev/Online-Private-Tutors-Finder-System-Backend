package com.example.tutorsFinderSystem.repositories;

import com.example.tutorsFinderSystem.entities.CalendarClass;
import com.example.tutorsFinderSystem.entities.Learner;
import com.example.tutorsFinderSystem.entities.Tutor;
import com.example.tutorsFinderSystem.enums.DayOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface CalendarClassRepository extends JpaRepository<CalendarClass, Long> {

    List<CalendarClass> findByClassRequest_RequestId(Long requestId);

    @Query("""
    SELECT COUNT(c) > 0
    FROM CalendarClass c
    WHERE c.classRequest.tutor = :tutor
      AND c.dayOfWeek = :dayOfWeek
      AND :date BETWEEN c.classRequest.startDate AND c.classRequest.endDate
      AND c.startTime < :endTime
      AND c.endTime > :startTime
""")
    boolean hasTimeConflictForTutorOnDate(
            @Param("tutor") Tutor tutor,
            @Param("dayOfWeek") DayOfWeek dayOfWeek,
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );

    @Query("""
        SELECT COUNT(c) > 0
        FROM CalendarClass c
        WHERE c.classRequest.learner = :learner
          AND c.dayOfWeek = :dayOfWeek
          AND :date BETWEEN c.classRequest.startDate AND c.classRequest.endDate
          AND c.startTime < :endTime
          AND c.endTime > :startTime
    """)
    boolean hasTimeConflictForLearnerOnDate(@Param("learner") Learner learner,
                                            @Param("dayOfWeek") DayOfWeek dayOfWeek,
                                            @Param("date") LocalDate date,
                                            @Param("startTime") LocalTime startTime,
                                            @Param("endTime") LocalTime endTime);



}
