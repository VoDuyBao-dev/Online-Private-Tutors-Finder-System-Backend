package com.example.tutorsFinderSystem.services;

import com.example.tutorsFinderSystem.entities.CalendarClass;
import com.example.tutorsFinderSystem.entities.ClassRequest;
import com.example.tutorsFinderSystem.entities.RequestSchedule;
import com.example.tutorsFinderSystem.repositories.CalendarClassRepository;
import com.example.tutorsFinderSystem.repositories.RequestScheduleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CalendarClassService {
    CalendarClassRepository calendarClassRepository;
    RequestScheduleRepository requestScheduleRepository;

    public void createTrialCalendar(ClassRequest request, RequestSchedule schedule) {
        CalendarClass calendar = CalendarClass.builder()
                .classRequest(request)
                .dayOfWeek(schedule.getDayOfWeek())
                .startTime(schedule.getStartTime())
                .endTime(schedule.getEndTime())
                .studyDate(request.getStartDate())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        calendarClassRepository.save(calendar);
    }

    public void createOfficialCalendar(ClassRequest request) {

        List<RequestSchedule> schedules =
                requestScheduleRepository.findByClassRequest_RequestId(
                        request.getRequestId()
                );

        LocalDate start = request.getStartDate();
        LocalDate end   = request.getEndDate();

        List<CalendarClass> calendars = new ArrayList<>();

        for (RequestSchedule schedule : schedules) {

            DayOfWeek targetDay = schedule.getDayOfWeek().toJavaDayOfWeek();

            LocalDate firstDate = start.with(
                    TemporalAdjusters.nextOrSame(targetDay)
            );

            for (LocalDate d = firstDate;
                 !d.isAfter(end);
                 d = d.plusWeeks(1)) {

                calendars.add(CalendarClass.builder()
                        .classRequest(request)
                        .studyDate(d)
                        .dayOfWeek(schedule.getDayOfWeek())
                        .startTime(schedule.getStartTime())
                        .endTime(schedule.getEndTime())
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build());
            }
        }

        calendarClassRepository.saveAll(calendars);
    }




}
