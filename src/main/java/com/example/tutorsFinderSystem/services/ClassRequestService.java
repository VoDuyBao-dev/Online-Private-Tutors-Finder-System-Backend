package com.example.tutorsFinderSystem.services;

import com.example.tutorsFinderSystem.dto.common.ClassRequestDTO;
import com.example.tutorsFinderSystem.dto.common.WeeklyScheduleDTO;
import com.example.tutorsFinderSystem.dto.request.OfficialClassRequest;
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
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

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

        //Check tutor có dạy môn này không
        validateTutorTeachesSubject(tutor, subject);

        //Check time hợp lệ
        validateTimeSlot(trialRequest.getStartTime(), trialRequest.getEndTime());

        //Check duplicate TRIAL cùng môn cùng tutor (chỉ PENDING)
        validateNoDuplicateTrialRequest(learner, tutor, subject);

        //Check time conflict với lịch tutor
        validateHasTimeConflictTutor(tutor, trialRequest);

//        Check learner conflict (học viên có bận hay không)
        ValidateHasLearnerTimeConflict(learner, trialRequest);

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

//    Learner gửi yêu cầu học chính thức
    public void createOfficialRequest(OfficialClassRequest officialClassRequest) {

        String learnerEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        Learner learner = learnerRepository.findByUser_Email(learnerEmail)
                .orElseThrow(() -> new AppException(ErrorCode.LEARNER_USER_NOT_FOUND));

        Tutor tutor = tutorRepository.findById(officialClassRequest.getTutorId())
                .orElseThrow(() -> new AppException(ErrorCode.TUTOR_NOT_FOUND));

        Subject subject = subjectRepository.findById(officialClassRequest.getSubjectId())
                .orElseThrow(() -> new AppException(ErrorCode.SUBJECT_NOT_EXISTED));

        // kiểm tra tutor có dạy môn học này hay không
        if (!tutor.getSubjects().contains(subject)) {
            throw new AppException(ErrorCode.TUTOR_NOT_TEACH_SUBJECT);
        }

        // kiểm tra ngày bắt đầu – kết thúc hợp lệ
        if (officialClassRequest.getEndDate().isBefore(officialClassRequest.getStartDate())) {
            throw new AppException(ErrorCode.END_DATE_BEFORE_START_DATE);
        }

        // kiểm tra từng khung giờ hợp lệ (thời lượng tối thiểu, tối đa,…)
        for (WeeklyScheduleDTO s : officialClassRequest.getSchedules()) {
            validateTimeSlot(s.getStartTime(), s.getEndTime());
        }

        // Sinh toàn bộ ngày học thực tế trong khoảng startDate – endDate
        List<LocalDate> sessionDates = generateAllSessionDates(
                officialClassRequest.getStartDate(),
                officialClassRequest.getEndDate(),
                officialClassRequest.getSchedules()
        );

        if (sessionDates.isEmpty()) {
            throw new AppException(ErrorCode.NO_SESSION_GENERATED);
        }

        // Kiểm tra trùng giờ cho từng ngày học (trùng với tutor hoặc learner)
        for (WeeklyScheduleDTO sched : officialClassRequest.getSchedules()) {

            // Lấy tất cả các ngày tương ứng với từng lịch theo thứ trong tuần
            List<LocalDate> datesForThisDay = generateDatesForSingleSchedule(
                    officialClassRequest.getStartDate(),
                    officialClassRequest.getEndDate(),
                    sched
            );

            for (LocalDate d : datesForThisDay) {

                // Kiểm tra trùng với lịch đã được xác nhận (calendar) của tutor
                boolean tutorConflictInCalendar = calendarClassRepository.hasTimeConflictForTutorOnDate(
                        tutor, sched.getDayOfWeek(), d, sched.getStartTime(), sched.getEndTime()
                );
                if (tutorConflictInCalendar) {
                    throw new AppException(ErrorCode.TUTOR_TIME_CONFLICT);
                }

                // Kiểm tra trùng lịch đã xác nhận trong calendar của learner
                boolean learnerConflictInCalendar = calendarClassRepository.hasTimeConflictForLearnerOnDate(
                        learner, sched.getDayOfWeek(), d, sched.getStartTime(), sched.getEndTime()
                );
                if (learnerConflictInCalendar) {
                    throw new AppException(ErrorCode.LEARNER_TIME_CONFLICT);
                }

                // Kiểm tra trùng với các request đang hoạt động (PENDING hoặc CONFIRMED chưa completed) của tutor
                List<RequestSchedule> tutorActiveReqSchedules =
                        requestScheduleRepository.findActiveTutorSchedulesByDay(
                                tutor.getTutorId(),
                                sched.getDayOfWeek()
                        );

                for (RequestSchedule existing : tutorActiveReqSchedules) {

                    ClassRequest existingCr = existing.getClassRequest();

                    // kiểm tra ngày thuộc trong khoảng date range của request hiện tại
                    if (!d.isBefore(existingCr.getStartDate()) && !d.isAfter(existingCr.getEndDate())) {

                        // kiểm tra trùng giờ
                        if (timeOverlap(existing.getStartTime(), existing.getEndTime(),
                                sched.getStartTime(), sched.getEndTime())) {

                            // nếu request cũ đang PENDING → trùng lịch
                            if (existingCr.getStatus() == ClassRequestStatus.PENDING) {
                                throw new AppException(ErrorCode.TIME_CONFLICT);
                            }

                            // nếu CONFIRMED và class chưa completed → trùng lịch
                            if (existingCr.getStatus() == ClassRequestStatus.CONFIRMED) {
                                ClassEntity ce = classRepository.findByClassRequest_RequestId(existingCr.getRequestId())
                                        .orElseThrow(() -> new AppException(ErrorCode.CLASS_NOT_FOUND));

                                if (ce.getStatus() != ClassStatus.COMPLETED) {
                                    throw new AppException(ErrorCode.TIME_CONFLICT);
                                }
                            }
                        }
                    }
                }

                // Kiểm tra trùng với các request đang hoạt động của learner
                List<RequestSchedule> learnerActiveReqSchedules =
                        requestScheduleRepository.findActiveLearnerSchedulesByDay(
                                learner.getLearnerId(),
                                sched.getDayOfWeek()
                        );

                for (RequestSchedule existing : learnerActiveReqSchedules) {

                    ClassRequest existingCr = existing.getClassRequest();

                    if (!d.isBefore(existingCr.getStartDate()) && !d.isAfter(existingCr.getEndDate())) {

                        if (timeOverlap(existing.getStartTime(), existing.getEndTime(),
                                sched.getStartTime(), sched.getEndTime())) {

                            if (existingCr.getStatus() == ClassRequestStatus.PENDING) {
                                throw new AppException(ErrorCode.TIME_CONFLICT);
                            }

                            if (existingCr.getStatus() == ClassRequestStatus.CONFIRMED) {
                                ClassEntity ce = classRepository.findByClassRequest_RequestId(existingCr.getRequestId())
                                        .orElseThrow(() -> new AppException(ErrorCode.CLASS_NOT_FOUND));

                                if (ce.getStatus() != ClassStatus.COMPLETED) {
                                    throw new AppException(ErrorCode.TIME_CONFLICT);
                                }
                            }
                        }
                    }
                }
            }
        }

        //tạo ClassRequest
        ClassRequest request = ClassRequest.builder()
                .learner(learner)
                .tutor(tutor)
                .subject(subject)
                .type(ClassRequestType.OFFICIAL)
                .status(ClassRequestStatus.PENDING)
                .startDate(officialClassRequest.getStartDate())
                .endDate(officialClassRequest.getEndDate())
                .sessionsPerWeek(officialClassRequest.getSchedules().size())
                .totalSessions(sessionDates.size())
                .additionalNotes(officialClassRequest.getAdditionalNotes())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        request = classRequestRepository.save(request);

        // tạo danh sách request_schedules cho từng buổi học trong tuần
        for (WeeklyScheduleDTO s : officialClassRequest.getSchedules()) {
            RequestSchedule rs = RequestSchedule.builder()
                    .classRequest(request)
                    .dayOfWeek(s.getDayOfWeek())
                    .startTime(s.getStartTime())
                    .endTime(s.getEndTime())
                    .createdAt(LocalDateTime.now())
                    .build();
            requestScheduleRepository.save(rs);
        }

        // tạo ClassEntity
        ClassEntity classEntity = ClassEntity.builder()
                .classRequest(request)
                .status(ClassStatus.PENDING)
                .completedSessions(0)
                .build();

        classRepository.save(classEntity);
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

    private void validateNoDuplicateTrialRequest(Learner learner, Tutor tutor, Subject subject) {
        boolean hasPendingTrial = classRequestRepository
                .existsDuplicateTrial(
                        learner,
                        tutor,
                        subject,
                        ClassRequestType.TRIAL,
                        List.of(ClassRequestStatus.PENDING)
                );


        if (hasPendingTrial) {
            throw new AppException(ErrorCode.DUPLICATE_TRIAL_REQUEST);
        }
    }

    private void validateHasTimeConflictTutor(Tutor tutor, TrialRequest trialRequest) {
        boolean hasConflict = calendarClassRepository.hasTimeConflictForTutorOnDate(
                tutor,
                trialRequest.getDayOfWeek(),
                trialRequest.getTrialDate(),
                trialRequest.getStartTime(),
                trialRequest.getEndTime()
        );

        if (hasConflict) {
            throw new AppException(ErrorCode.TUTOR_TIME_CONFLICT);
        }
    }

    private void ValidateHasLearnerTimeConflict(Learner learner, TrialRequest trialRequest) {
        // Check learner conflict (học viên có bận hay không)
        boolean learnerConflict = requestScheduleRepository.hasLearnerTrialConflict(
                learner,
                trialRequest.getDayOfWeek(),
                trialRequest.getTrialDate(),
                trialRequest.getStartTime(),
                trialRequest.getEndTime()
        );
        if (learnerConflict) {
            throw new AppException(ErrorCode.LEARNER_TIME_CONFLICT);
        }
    }

    private boolean timeOverlap(LocalTime existingStart, LocalTime existingEnd, LocalTime newStart, LocalTime newEnd) {
        return existingStart.isBefore(newEnd) && newStart.isBefore(existingEnd);
    }

    // tạo ngày cho một Lịch hàng tuần (ví dụ: mỗi thứ Hai giữa ngày bắt đầu và kết thúc)
    private List<LocalDate> generateDatesForSingleSchedule(LocalDate start, LocalDate end, WeeklyScheduleDTO sched) {
        java.time.DayOfWeek target = sched.getDayOfWeek().toJavaDayOfWeek();
        LocalDate first = start.with(TemporalAdjusters.nextOrSame(target));

        List<LocalDate> res = new ArrayList<>();
        for (LocalDate d = first; !d.isAfter(end); d = d.plusWeeks(1)) {
            res.add(d);
        }
        return res;
    }

    // tạo tất cả các ngày buổi học theo nhiều lịch hàng tuần, gộp và loại bỏ trùng lặp, sắp xếp
    private List<LocalDate> generateAllSessionDates(LocalDate start, LocalDate end, List<WeeklyScheduleDTO> schedules) {
        Set<LocalDate> set = new HashSet<>();
        for (WeeklyScheduleDTO s : schedules) {
            set.addAll(generateDatesForSingleSchedule(start, end, s));
        }
        List<LocalDate> list = new ArrayList<>(set);
        Collections.sort(list);
        return list;
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
