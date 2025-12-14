package com.example.tutorsFinderSystem.repositories;

import com.example.tutorsFinderSystem.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
