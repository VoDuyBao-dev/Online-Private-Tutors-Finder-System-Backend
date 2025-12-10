package com.example.tutorsFinderSystem.repositories;

import com.example.tutorsFinderSystem.entities.CalendarClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalendarClassRepository extends JpaRepository<CalendarClass, Long> {

    List<CalendarClass> findByClassRequest_RequestId(Long requestId);
}
