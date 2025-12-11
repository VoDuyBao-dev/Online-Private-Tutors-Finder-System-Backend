package com.example.tutorsFinderSystem.services;

import com.example.tutorsFinderSystem.dto.common.ClassRequestDTO;
import com.example.tutorsFinderSystem.entities.ClassRequest;
import com.example.tutorsFinderSystem.enums.ClassRequestStatus;
import com.example.tutorsFinderSystem.enums.ClassRequestType;
import com.example.tutorsFinderSystem.mapper.ClassRequestMapper;
import com.example.tutorsFinderSystem.repositories.ClassRequestRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClassRequestService {
    ClassRequestRepository classRequestRepository;
    ClassRequestMapper classRequestMapper;

    public Page<ClassRequestDTO> getClassRequestDetails(int page, int size) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Pageable pageable = PageRequest.of(page, size);

        Page<ClassRequest> classRequests = classRequestRepository.findByLearner_User_Email(email, pageable);

        return classRequests.map(classRequestMapper::toClassRequestDTO);

    }



}
