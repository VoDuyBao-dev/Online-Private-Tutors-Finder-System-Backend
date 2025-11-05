package com.example.tutorsFinderSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tutorsFinderSystem.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
    boolean existsByEmail(String email);
}
