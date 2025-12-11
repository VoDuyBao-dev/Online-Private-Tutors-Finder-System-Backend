package com.example.tutorsFinderSystem.services;

import com.example.tutorsFinderSystem.dto.common.ClassRequestDTO;
import com.example.tutorsFinderSystem.dto.request.TrialRequest;
import com.example.tutorsFinderSystem.entities.*;
import com.example.tutorsFinderSystem.enums.ClassRequestStatus;
import com.example.tutorsFinderSystem.enums.ClassRequestType;
import com.example.tutorsFinderSystem.enums.ClassStatus;
import com.example.tutorsFinderSystem.exceptions.AppException;
import com.example.tutorsFinderSystem.exceptions.ErrorCode;
import com.example.tutorsFinderSystem.mapper.ClassRequestMapper;
import com.example.tutorsFinderSystem.repositories.*;
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
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClassRequestService {
    ClassRequestRepository classRequestRepository;
    ClassRequestMapper classRequestMapper;

    ClassRepository classRepository;
    CalendarClassRepository calendarClassRepository;
    RequestScheduleRepository requestScheduleRepository;
    LearnerRepository learnerRepository;
    TutorRepository tutorRepository;
    SubjectRepository subjectRepository;

    public Page<ClassRequestDTO> getClassRequestDetails(int page, int size) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Pageable pageable = PageRequest.of(page, size);

        Page<ClassRequest> classRequests = classRequestRepository.findByLearner_User_Email(email, pageable);

        return classRequests.map(classRequestMapper::toClassRequestDTO);

    }

//    learner gửi yêu cầu học thử
    public void createTrialRequest(TrialRequest trialRequest) {
        String learnerEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        Learner learner = learnerRepository.findByUser_Email(learnerEmail)
                .orElseThrow(() -> new AppException(ErrorCode.LEARNER_USER_NOT_FOUND));

        log.info("Creating trial request for learner: {}", learner.getLearnerId());

        Tutor tutor = tutorRepository.findById(trialRequest.getTutorId())
                .orElseThrow(() -> new AppException(ErrorCode.TUTOR_NOT_FOUND));

        Subject subject = subjectRepository.findById(trialRequest.getSubjectId())
                .orElseThrow(() -> new AppException(ErrorCode.SUBJECT_NOT_EXISTED));


        validateTutorTeachesSubject(tutor, subject);
        validateTimeSlot(trialRequest.getStartTime(), trialRequest.getEndTime());
        validateNoDuplicateTrialRequest(learner, tutor);

        //Check time conflict với lịch tutor
        boolean hasConflict = calendarClassRepository.hasTimeConflict(
                tutor,
                trialRequest.getDayOfWeek(),
                trialRequest.getStartTime(),
                trialRequest.getEndTime()
        );

        if (hasConflict) {
            throw new AppException(ErrorCode.TUTOR_TIME_CONFLICT);
        }


        ClassRequest classRequest = ClassRequest.builder()
                .learner(learner)
                .tutor(tutor)
                .subject(subject)
                .type(ClassRequestType.TRIAL)
                .status(ClassRequestStatus.PENDING)
                .startDate(trialRequest.getTrialDate())
                .endDate(trialRequest.getTrialDate())
                .sessionsPerWeek(1)
                .totalSessions(1)
                .additionalNotes(trialRequest.getAdditionalNotes())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        classRequest = classRequestRepository.save(classRequest);


        ClassEntity classEntity = ClassEntity.builder()
                .classRequest(classRequest)
                .status(ClassStatus.PENDING)
                .completedSessions(0)
                .build();

        classRepository.save(classEntity);

        // tạo RequestSchedule để sau này copy sang CalendarClass
        RequestSchedule schedule = RequestSchedule.builder()
                .classRequest(classRequest)
                .dayOfWeek(trialRequest.getDayOfWeek())
                .startTime(trialRequest.getStartTime())
                .endTime(trialRequest.getEndTime())
                .createdAt(LocalDateTime.now())
                .build();

        requestScheduleRepository.save(schedule);

        log.info("Trial request created successfully with ID: {}", classRequest.getRequestId());


    }

    private void validateTutorTeachesSubject(Tutor tutor, Subject subject) {
        if (!tutor.getSubjects().contains(subject)) {
            throw new AppException(ErrorCode.TUTOR_NOT_TEACH_SUBJECT);
        }
    }

    private void validateTimeSlot(LocalTime start, LocalTime end) {
        if (end.isBefore(start) || end.equals(start)) {
            throw new AppException(ErrorCode.END_TIME_BEFORE_START_TIME);
        }

        long duration = ChronoUnit.MINUTES.between(start, end);
        if (duration < 30) {
            throw new AppException(ErrorCode.CLASS_DURATION_TOO_SHORT);
        }
        if (duration > 90) {
            throw new AppException(ErrorCode.CLASS_DURATION_EXCEEDS_MAX);
        }
    }

    private void validateNoDuplicateTrialRequest(Learner learner, Tutor tutor) {
        boolean hasPendingTrial = classRequestRepository
                .existsByLearnerAndTutorAndTypeAndStatusIn(
                        learner,
                        tutor,
                        ClassRequestType.TRIAL,
                        List.of(ClassRequestStatus.PENDING, ClassRequestStatus.CONFIRMED)
                );

        if (hasPendingTrial) {
            throw new AppException(ErrorCode.DUPLICATE_TRIAL_REQUEST);
        }
    }

//    private void validateDateRange(LocalDate start, LocalDate end) {
//        if (end.isBefore(start)) {
//            throw new BusinessException("End date must be after start date");
//        }
//
//        // Optional: validate minimum duration (e.g., at least 1 month)
//        long weeks = ChronoUnit.WEEKS.between(start, end);
//        if (weeks < 4) {
//            throw new BusinessException("Official class must be at least 4 weeks long");
//        }
//    }
//
//    private void validateSchedulesMatchSessionsPerWeek(
//            List<OfficialClassRequestDTO.ScheduleSlotDTO> schedules,
//            Integer sessionsPerWeek) {
//
//        if (schedules.size() != sessionsPerWeek) {
//            throw new BusinessException(
//                    String.format("Number of schedules (%d) must match sessions per week (%d)",
//                            schedules.size(), sessionsPerWeek)
//            );
//        }
//    }
//
//    private void validateTutorOwnsRequest(Long tutorId, ClassRequest request) {
//        if (!request.getTutor().getTutorId().equals(tutorId)) {
//            throw new BusinessException("You don't have permission to perform this action");
//        }
//    }
//
//    private void validateRequestIsPending(ClassRequest request) {
//        if (request.getStatus() != ClassRequestStatus.PENDING) {
//            throw new BusinessException(
//                    String.format("Can only approve/reject pending requests. Current status: %s",
//                            request.getStatus())
//            );
//        }
//    }




}
