package com.example.tutorsFinderSystem.repositories;

import com.example.tutorsFinderSystem.entities.CalendarClass;
import com.example.tutorsFinderSystem.entities.Tutor;
import com.example.tutorsFinderSystem.enums.DayOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.List;

public interface CalendarClassRepository extends JpaRepository<CalendarClass, Long> {

    List<CalendarClass> findByClassRequest_RequestId(Long requestId);

    @Query("""
        SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END
        FROM CalendarClass c
        WHERE c.classRequest.tutor = :tutor
          AND c.dayOfWeek = :day
          AND (
                (c.startTime < :end AND c.endTime > :start)
          )
    """)
    boolean hasTimeConflict(
            @Param("tutor") Tutor tutor,
            @Param("day") DayOfWeek day,
            @Param("start") LocalTime start,
            @Param("end") LocalTime end
    );
}
