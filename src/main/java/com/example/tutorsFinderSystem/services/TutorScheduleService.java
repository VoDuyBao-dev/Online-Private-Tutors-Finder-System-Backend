package com.example.tutorsFinderSystem.services;

import com.example.tutorsFinderSystem.dto.request.TutorAvailabilityCreateRequest;
import com.example.tutorsFinderSystem.dto.request.TutorAvailabilityUpdateRequest;
import com.example.tutorsFinderSystem.dto.response.TutorAvailabilityResponse;
import com.example.tutorsFinderSystem.entities.Tutor;
import com.example.tutorsFinderSystem.entities.TutorAvailability;
import com.example.tutorsFinderSystem.enums.TutorAvailabilityStatus;
// import com.example.tutorsFinderSystem.enums.DayOfWeek;
// import com.example.tutorsFinderSystem.enums.TutorAvailabilityStatus;
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
// import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TutorScheduleService {

    private final TutorRepository tutorRepository;
    private final TutorAvailabilityRepository availabilityRepository;
    private final TutorAvailabilityMapper availabilityMapper;

    // private static final DateTimeFormatter TIME_FORMAT =
    // DateTimeFormatter.ofPattern("HH:mm");

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

        LocalDate date = LocalDate.parse(request.getDate()); // yyyy-MM-dd
        LocalTime start = LocalTime.parse(request.getStartTime());
        LocalTime end = LocalTime.parse(request.getEndTime());

        if (!end.isAfter(start)) {
            throw new AppException(ErrorCode.INVALID_TIME_RANGE);
        }

        LocalDateTime startDateTime = date.atTime(start);
        LocalDateTime endDateTime = date.atTime(end);

        boolean exists = availabilityRepository
                .existsByTutor_TutorIdAndStartTimeAndEndTime(
                        tutor.getTutorId(), startDateTime, endDateTime);

        if (exists) {
            throw new AppException(ErrorCode.AVAILABILITY_CONFLICT);
        }

        if (request.getStatus() == TutorAvailabilityStatus.BOOKED) {
            throw new AppException(ErrorCode.INVALID_AVAILABILITY_STATUS);
        }

        LocalDateTime now = LocalDateTime.now();

        TutorAvailability entity = TutorAvailability.builder()
                .tutor(tutor)
                .startTime(startDateTime)
                .endTime(endDateTime)
                .status(request.getStatus())
                .createdAt(now)
                .updatedAt(now)
                .build();

        availabilityRepository.save(entity);

        return availabilityMapper.toResponse(entity);
    }

    @Transactional
    public TutorAvailabilityResponse updateAvailability(Long id,
            TutorAvailabilityUpdateRequest request) {

        Tutor tutor = getCurrentTutor();

        TutorAvailability availability = availabilityRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TUTOR_AVAILABILITY_NOT_FOUND));

        if (!availability.getTutor().getTutorId().equals(tutor.getTutorId())) {
            throw new AppException(ErrorCode.TUTOR_AVAILABILITY_NOT_FOUND);
        }

        LocalDate date = LocalDate.parse(request.getDate());
        LocalTime start = LocalTime.parse(request.getStartTime());
        LocalTime end = LocalTime.parse(request.getEndTime());

        if (!end.isAfter(start)) {
            throw new AppException(ErrorCode.INVALID_TIME_RANGE);
        }

        if (request.getStatus() == TutorAvailabilityStatus.BOOKED) {
            throw new AppException(ErrorCode.INVALID_AVAILABILITY_STATUS);
        }

        LocalDateTime startDateTime = date.atTime(start);
        LocalDateTime endDateTime = date.atTime(end);

        boolean exists = availabilityRepository
                .existsByTutor_TutorIdAndStartTimeAndEndTime(
                        tutor.getTutorId(), startDateTime, endDateTime);

        if (exists && (!startDateTime.equals(availability.getStartTime())
                || !endDateTime.equals(availability.getEndTime()))) {

            throw new AppException(ErrorCode.AVAILABILITY_CONFLICT);
        }

        availability.setStartTime(startDateTime);
        availability.setEndTime(endDateTime);
        availability.setStatus(request.getStatus());
        availability.setUpdatedAt(LocalDateTime.now());

        availabilityRepository.save(availability);

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

    // private LocalDate getNextDateOfWeek(DayOfWeek dayOfWeek) {
    // LocalDate today = LocalDate.now();
    // java.time.DayOfWeek target = java.time.DayOfWeek.valueOf(dayOfWeek.name());
    // int diff = target.getValue() - today.getDayOfWeek().getValue();
    // if (diff < 0)
    // diff += 7;
    // return today.plusDays(diff);
    // }
}
