package com.example.tutorsFinderSystem.repositories;

import com.example.tutorsFinderSystem.entities.ClassRequest;
import com.example.tutorsFinderSystem.entities.RequestSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestScheduleRepository extends JpaRepository<RequestSchedule, Long> {
    List<RequestSchedule> findByClassRequest(ClassRequest classRequest);

    void deleteByClassRequest(ClassRequest classRequest);



}
