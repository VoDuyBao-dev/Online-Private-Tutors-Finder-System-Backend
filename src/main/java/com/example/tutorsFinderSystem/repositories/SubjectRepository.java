package com.example.tutorsFinderSystem.repositories;

import com.example.tutorsFinderSystem.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
