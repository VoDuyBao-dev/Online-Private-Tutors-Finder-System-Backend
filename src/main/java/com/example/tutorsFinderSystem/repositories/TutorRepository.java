package com.example.tutorsFinderSystem.repositories;

import com.example.tutorsFinderSystem.entities.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface TutorRepository extends JpaRepository<Tutor, Long> {
    Optional<Tutor> findByUserUserId(Long userId);
}
