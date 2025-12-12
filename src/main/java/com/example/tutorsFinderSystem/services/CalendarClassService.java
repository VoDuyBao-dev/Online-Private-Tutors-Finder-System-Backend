package com.example.tutorsFinderSystem.services;

import com.example.tutorsFinderSystem.entities.CalendarClass;
import com.example.tutorsFinderSystem.entities.ClassRequest;
import com.example.tutorsFinderSystem.entities.RequestSchedule;
import com.example.tutorsFinderSystem.repositories.CalendarClassRepository;
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

    public void createTrialCalendar(ClassRequest request, RequestSchedule schedule) {
        CalendarClass calendar = CalendarClass.builder()
                .classRequest(request)
                .dayOfWeek(schedule.getDayOfWeek())
                .startTime(schedule.getStartTime())
                .endTime(schedule.getEndTime())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        calendarClassRepository.save(calendar);
    }

    public void createOfficialCalendar(ClassRequest request, RequestSchedule schedule) {

        int totalSessions = request.getTotalSessions();

        // Chuyển enum custom DayOfWeek sang java.time.DayOfWeek
        DayOfWeek targetDay = schedule.getDayOfWeek().toJavaDayOfWeek();

        LocalDate start = request.getStartDate();
        LocalDate end = request.getEndDate();

        // Tìm ngày đầu tiên trùng thứ (MONDAY / TUESDAY / ...)
        LocalDate first = start.with(TemporalAdjusters.nextOrSame(targetDay));

        List<CalendarClass> list = new ArrayList<>();

        // Nhảy theo tuần
        for (LocalDate d = first;
             !d.isAfter(end) && list.size() < totalSessions;
             d = d.plusWeeks(1)) {

            list.add(CalendarClass.builder()
                    .classRequest(request)
                    .dayOfWeek(schedule.getDayOfWeek())
                    .startTime(schedule.getStartTime())
                    .endTime(schedule.getEndTime())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build());


        }

        calendarClassRepository.saveAll(list);
    }



}
