package com.example.tutorsFinderSystem.services;

import com.example.tutorsFinderSystem.dto.request.TutorAvailabilityCreateRequest;
import com.example.tutorsFinderSystem.dto.request.TutorAvailabilityUpdateRequest;
import com.example.tutorsFinderSystem.dto.response.TutorAvailabilityResponse;
import com.example.tutorsFinderSystem.entities.Tutor;
import com.example.tutorsFinderSystem.entities.TutorAvailability;
import com.example.tutorsFinderSystem.enums.DayOfWeek;
import com.example.tutorsFinderSystem.enums.TutorAvailabilityStatus;
import com.example.tutorsFinderSystem.exceptions.AppException;
import com.example.tutorsFinderSystem.exceptions.ErrorCode;
import com.example.tutorsFinderSystem.mapper.TutorAvailabilityMapper;
import com.example.tutorsFinderSystem.repositories.TutorAvailabilityRepository;
import com.example.tutorsFinderSystem.repositories.TutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TutorScheduleService {

    private final TutorRepository tutorRepository;
    private final TutorAvailabilityRepository availabilityRepository;
    private final TutorAvailabilityMapper availabilityMapper;

    private static final DateTimeFormatter TIME_FORMAT =
            DateTimeFormatter.ofPattern("HH:mm");

    private Tutor getCurrentTutor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return tutorRepository.findByUser_Email(email)
                .orElseThrow(() -> new AppException(ErrorCode.TUTOR_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public List<TutorAvailabilityResponse> getMyAvailability() {
        Tutor tutor = getCurrentTutor();
        List<TutorAvailability> list = availabilityRepository
                .findByTutor_TutorIdOrderByStartTimeAsc(tutor.getTutorId());
        return availabilityMapper.toResponses(list);
    }

    @Transactional
    public TutorAvailabilityResponse createAvailability(TutorAvailabilityCreateRequest request) {
        Tutor tutor = getCurrentTutor();

        LocalTime start = LocalTime.parse(request.getStartTime(), TIME_FORMAT);
        LocalTime end = LocalTime.parse(request.getEndTime(), TIME_FORMAT);
        if (!end.isAfter(start)) {
            throw new AppException(ErrorCode.INVALID_TIME_RANGE);
        }

        if (request.getStatus() == TutorAvailabilityStatus.BOOKED) {
            throw new AppException(ErrorCode.INVALID_AVAILABILITY_STATUS);
        }

        LocalDate dateForSlot = getNextDateOfWeek(request.getDayOfWeek());
        LocalDateTime startDateTime = dateForSlot.atTime(start);
        LocalDateTime endDateTime = dateForSlot.atTime(end);

        boolean exists = availabilityRepository
                .existsByTutor_TutorIdAndStartTimeAndEndTimeAndStatus(
                        tutor.getTutorId(), startDateTime, endDateTime, request.getStatus()
                );

        if (exists) {
            throw new AppException(ErrorCode.DUPLICATED_AVAILABILITY_SLOT);
        }

        LocalDateTime now = LocalDateTime.now();

        TutorAvailability availability = TutorAvailability.builder()
                .tutor(tutor)
                .startTime(startDateTime)
                .endTime(endDateTime)
                .status(request.getStatus())
                .createdAt(now)   // ngày tạo = ngay bây giờ
                .updatedAt(now)
                .build();

        availability = availabilityRepository.save(availability);
        return availabilityMapper.toResponse(availability);
    }

    @Transactional
    public TutorAvailabilityResponse updateAvailability(Long id,
                                                        TutorAvailabilityUpdateRequest request) {
        Tutor tutor = getCurrentTutor();

        TutorAvailability availability = availabilityRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TUTOR_AVAILABILITY_NOT_FOUND));

        if (!availability.getTutor().getTutorId().equals(tutor.getTutorId())) {
            // Không cho sửa lịch của người khác
            throw new AppException(ErrorCode.TUTOR_AVAILABILITY_NOT_FOUND);
        }

        LocalTime start = LocalTime.parse(request.getStartTime(), TIME_FORMAT);
        LocalTime end = LocalTime.parse(request.getEndTime(), TIME_FORMAT);
        if (!end.isAfter(start)) {
            throw new AppException(ErrorCode.INVALID_TIME_RANGE);
        }

        if (request.getStatus() == TutorAvailabilityStatus.BOOKED) {
            throw new AppException(ErrorCode.INVALID_AVAILABILITY_STATUS);
        }

        LocalDate dateForSlot = getNextDateOfWeek(request.getDayOfWeek());
        LocalDateTime startDateTime = dateForSlot.atTime(start);
        LocalDateTime endDateTime = dateForSlot.atTime(end);

        availability.setStartTime(startDateTime);
        availability.setEndTime(endDateTime);
        availability.setStatus(request.getStatus());
        availability.setUpdatedAt(LocalDateTime.now());

        availability = availabilityRepository.save(availability);
        return availabilityMapper.toResponse(availability);
    }

    @Transactional
    public void deleteAvailability(Long id) {
        Tutor tutor = getCurrentTutor();

        TutorAvailability availability = availabilityRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TUTOR_AVAILABILITY_NOT_FOUND));

        if (!availability.getTutor().getTutorId().equals(tutor.getTutorId())) {
            throw new AppException(ErrorCode.TUTOR_AVAILABILITY_NOT_FOUND);
        }

        availabilityRepository.delete(availability);
    }

    private LocalDate getNextDateOfWeek(DayOfWeek dayOfWeek) {
        LocalDate today = LocalDate.now();
        java.time.DayOfWeek target = java.time.DayOfWeek.valueOf(dayOfWeek.name());
        int diff = target.getValue() - today.getDayOfWeek().getValue();
        if (diff < 0) diff += 7;
        return today.plusDays(diff);
    }
}
