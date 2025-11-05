package com.example.tutorsFinderSystem.repositories;

import com.example.tutorsFinderSystem.entities.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TutorRepository extends JpaRepository<Tutor, Long> {
    boolean existsByUser_Email(String email);
}
