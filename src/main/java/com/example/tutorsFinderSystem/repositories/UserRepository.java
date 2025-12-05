package com.example.tutorsFinderSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.tutorsFinderSystem.entities.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    List<User> findByEnabledFalseAndActivationExpiryTimeBefore(LocalDateTime expiryTime);

    Optional<User> findByEmail(String email);
}
