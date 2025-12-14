package com.example.tutorsFinderSystem.scheduler;

import com.example.tutorsFinderSystem.entities.CalendarClass;
import com.example.tutorsFinderSystem.entities.ClassEntity;
import com.example.tutorsFinderSystem.enums.ClassStatus;
import com.example.tutorsFinderSystem.repositories.CalendarClassRepository;
import com.example.tutorsFinderSystem.repositories.ClassRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class ClassCompletionScheduler {
    private final CalendarClassRepository calendarClassRepository;
    private final ClassRepository classRepository;

    @Scheduled(cron = "0 */15 * * * *")
    public void updateCompletedSessions() {
        log.info("Scheduler updateCompletedSessions start");

        LocalDateTime now = LocalDateTime.now();

        List<CalendarClass> finishedClasses = calendarClassRepository.findByCompletedFalse();


        for (CalendarClass calendar : finishedClasses) {

            LocalDateTime endDateTime = LocalDateTime.of(calendar.getStudyDate(), calendar.getEndTime());

            if (endDateTime.isAfter(now)) {
                continue;
            }

            ClassEntity classEntity = classRepository.findByClassRequest_RequestId(
                            calendar.getClassRequest().getRequestId()
                    ).orElse(null);

            if (classEntity == null) {
                continue;
            }

            // Chỉ xử lý lớp đang học
            if (classEntity.getStatus() != ClassStatus.ONGOING) {
                continue;
            }

            // Tăng số buổi đã học
            classEntity.setCompletedSessions(
                    classEntity.getCompletedSessions() + 1
            );

            calendar.setCompleted(true);

            // Check hoàn thành
            if (classEntity.getCompletedSessions()
                    >= classEntity.getClassRequest().getTotalSessions()) {

                classEntity.setStatus(ClassStatus.COMPLETED);
                log.info("Class {} COMPLETED", classEntity.getClassId());
            }


            classRepository.save(classEntity);
        }
    }
}
