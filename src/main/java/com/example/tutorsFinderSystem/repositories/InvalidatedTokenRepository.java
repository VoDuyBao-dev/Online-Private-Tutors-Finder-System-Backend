package com.example.tutorsFinderSystem.repositories;

import com.example.tutorsFinderSystem.entities.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {
}
