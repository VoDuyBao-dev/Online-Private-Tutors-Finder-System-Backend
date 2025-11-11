package com.example.tutorsFinderSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tutorsFinderSystem.entities.User;

import java.time.LocalDateTime;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>{
    boolean existsByEmail(String email);
    List<User> findByEnabledFalseAndActivationExpiryTimeBefore(LocalDateTime expiryTime);
}
