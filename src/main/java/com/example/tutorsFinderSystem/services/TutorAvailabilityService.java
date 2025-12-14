package com.example.tutorsFinderSystem.services;

import com.example.tutorsFinderSystem.entities.CalendarClass;
import com.example.tutorsFinderSystem.entities.Tutor;
import com.example.tutorsFinderSystem.enums.TutorAvailabilityStatus;
import com.example.tutorsFinderSystem.repositories.TutorAvailabilityRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TutorAvailabilityService {
    private final TutorAvailabilityRepository tutorAvailabilityRepository;


    public void bookAvailabilityForCalendars(
            Tutor tutor,
            List<CalendarClass> calendars
    ) {
        for (CalendarClass calendar : calendars) {

            LocalDateTime start = LocalDateTime.of(
                    calendar.getStudyDate(),
                    calendar.getStartTime()
            );

            LocalDateTime end = LocalDateTime.of(
                    calendar.getStudyDate(),
                    calendar.getEndTime()
            );

            tutorAvailabilityRepository.findAvailableSlot(tutor.getTutorId(), start, end)
                    .ifPresent(availability -> {
                        availability.setStatus(TutorAvailabilityStatus.BOOKED);
                        tutorAvailabilityRepository.save(availability);
                    });
        }
    }

}
