package com.example.tutorsFinderSystem.services;

import com.example.tutorsFinderSystem.dto.response.TutorRequestClassResponse;
import com.example.tutorsFinderSystem.dto.response.TutorRequestStatusUpdateResponse;
import com.example.tutorsFinderSystem.entities.*;
import com.example.tutorsFinderSystem.enums.ClassRequestStatus;
import com.example.tutorsFinderSystem.enums.ClassRequestType;
import com.example.tutorsFinderSystem.enums.ClassStatus;
import com.example.tutorsFinderSystem.exceptions.AppException;
import com.example.tutorsFinderSystem.exceptions.ErrorCode;
import com.example.tutorsFinderSystem.mapper.TutorClassRequestMapper;
import com.example.tutorsFinderSystem.repositories.*;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TutorClassRequestService {

    private final ClassRequestRepository classRequestRepository;
    private final ClassRepository classRepository;
    private final CalendarClassRepository calendarClassRepository;
    private final TutorClassRequestMapper mapper;

    private final UserService userService;
    private final TutorRepository tutorRepository;

    private final CalendarClassService calendarClassService;
    private final RequestScheduleRepository requestScheduleRepository;



    /**
     * Lấy tutor hiện tại từ SecurityContext (qua UserService),
     * sau đó tìm Tutor tương ứng với User đó.
     */
    private Tutor getCurrentTutor() {
        User currentUser = userService.getCurrentUser();
        return tutorRepository.findByUserUserId(currentUser.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.TUTOR_NOT_FOUND));
    }

    /**
     * Lấy danh sách yêu cầu của tutor hiện tại với filter.
     */
    @Transactional(readOnly = true)
    public Page<TutorRequestClassResponse> getRequestsForCurrentTutor(
            ClassRequestStatus status,
            ClassRequestType type,
            String keyword,
            int page,
            int size) {

        Tutor tutor = getCurrentTutor();
        Long tutorId = tutor.getTutorId();

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<ClassRequest> requestPage;

        if (status != null && type != null) {
            requestPage = classRequestRepository
                    .findByTutor_TutorIdAndStatusAndType(tutorId, status, type, pageable);
        } else if (status != null) {
            requestPage = classRequestRepository
                    .findByTutor_TutorIdAndStatus(tutorId, status, pageable);
        } else if (type != null) {
            requestPage = classRequestRepository
                    .findByTutor_TutorIdAndType(tutorId, type, pageable);
        } else {
            requestPage = classRequestRepository
                    .findByTutor_TutorId(tutorId, pageable);
        }

        // Map sang DTO + filter theo keyword (tên học viên / tên môn học)
        List<TutorRequestClassResponse> content = requestPage.getContent().stream()
                .map(request -> {
                    ClassEntity classEntity = classRepository
                            .findByClassRequest_RequestId(request.getRequestId())
                            .orElseThrow(() -> new AppException(ErrorCode.CLASS_NOT_FOUND));

                    List<CalendarClass> slots = calendarClassRepository
                            .findByClassRequest_RequestId(request.getRequestId());

                    return mapper.toSummary(request, classEntity, slots);
                })
                .filter(dto -> matchesKeyword(dto, keyword))
                .collect(Collectors.toList());

        // totalElements vẫn dùng theo page gốc để không làm lệch phân trang
        return new PageImpl<>(content, pageable, requestPage.getTotalElements());
    }

    private boolean matchesKeyword(TutorRequestClassResponse dto, String keyword) {
        if (keyword == null || keyword.isBlank())
            return true;
        String kw = keyword.toLowerCase();
        return (dto.getFullName() != null && dto.getFullName().toLowerCase().contains(kw))
                || (dto.getSubjectName() != null && dto.getSubjectName().toLowerCase().contains(kw));
    }

    /**
     * Tutor hiện tại chấp nhận yêu cầu:
     *  - class_requests.status = CONFIRMED
     *  - classes.status        = ONGOING
     */
    @Transactional
    public TutorRequestStatusUpdateResponse acceptRequestForCurrentTutor(Long requestId) {
        Tutor tutor = getCurrentTutor();
        Long tutorId = tutor.getTutorId();

        ClassRequest request = classRequestRepository.findById(requestId)
                .orElseThrow(() -> new AppException(ErrorCode.CLASS_REQUEST_NOT_FOUND));

        if (!request.getTutor().getTutorId().equals(tutorId)) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }

        // chỉ cho phép accept nếu đang PENDING
        if (request.getStatus() != ClassRequestStatus.PENDING) {
            throw new AppException(ErrorCode.INVALID_REQUEST_STATUS);
        }

        request.setStatus(ClassRequestStatus.CONFIRMED);
        classRequestRepository.save(request);

        ClassEntity classEntity = classRepository.findByClassRequest_RequestId(requestId)
                .orElseThrow(() -> new AppException(ErrorCode.CLASS_NOT_FOUND));

        classEntity.setStatus(ClassStatus.ONGOING);
        classRepository.save(classEntity);

        RequestSchedule schedule = requestScheduleRepository
                .findByClassRequest_RequestId(requestId)
                .stream().findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.SCHEDULE_NOT_FOUND));

        //Tạo calendar học thử/ chính thức
        if (request.getType() == ClassRequestType.TRIAL) {
            calendarClassService.createTrialCalendar(request, schedule);
        } else {
            calendarClassService.createOfficialCalendar(request);
        }

        return TutorRequestStatusUpdateResponse.builder()
                .requestId(request.getRequestId())
                .classId(classEntity.getClassId())
                .requestStatus(request.getStatus())
                .classStatus(classEntity.getStatus())
                .build();
    }

    /**
     * Tutor hiện tại từ chối yêu cầu:
     *  - class_requests.status = CANCELLED
     *  - classes.status        = CANCELLED
     */
    @Transactional
    public TutorRequestStatusUpdateResponse rejectRequestForCurrentTutor(Long requestId) {
        Tutor tutor = getCurrentTutor();
        Long tutorId = tutor.getTutorId();

        ClassRequest request = classRequestRepository.findById(requestId)
                .orElseThrow(() -> new AppException(ErrorCode.CLASS_REQUEST_NOT_FOUND));

        if (!request.getTutor().getTutorId().equals(tutorId)) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }

        if (request.getStatus() == ClassRequestStatus.CANCELLED) {
            throw new AppException(ErrorCode.INVALID_REQUEST_STATUS);
        }

        request.setStatus(ClassRequestStatus.CANCELLED);
        classRequestRepository.save(request);

        ClassEntity classEntity = classRepository.findByClassRequest_RequestId(requestId)
                .orElseThrow(() -> new AppException(ErrorCode.CLASS_NOT_FOUND));

        classEntity.setStatus(ClassStatus.CANCELLED);
        classRepository.save(classEntity);

        return TutorRequestStatusUpdateResponse.builder()
                .requestId(request.getRequestId())
                .classId(classEntity.getClassId())
                .requestStatus(request.getStatus())
                .classStatus(classEntity.getStatus())
                .build();
    }

    // Nếu sau này cần xem chi tiết, bạn có thể bật lại và đổi sang dùng getCurrentTutor():

    // @Transactional(readOnly = true)
    // public TutorRequestDetailResponse getRequestDetailForCurrentTutor(Long requestId) {
    //     Tutor tutor = getCurrentTutor();
    //     Long tutorId = tutor.getTutorId();
    //
    //     ClassRequest request = classRequestRepository.findById(requestId)
    //             .orElseThrow(() -> new AppException(ErrorCode.CLASS_REQUEST_NOT_FOUND));
    //
    //     if (!request.getTutor().getTutorId().equals(tutorId)) {
    //         throw new AppException(ErrorCode.ACCESS_DENIED);
    //     }
    //
    //     ClassEntity classEntity =
    //             classRepository.findByClassRequest_RequestId(requestId)
    //                     .orElseThrow(() -> new AppException(ErrorCode.CLASS_NOT_FOUND));
    //
    //     List<CalendarClass> slots =
    //             calendarClassRepository.findByClassRequest_RequestId(requestId);
    //
    //     return mapper.toDetail(request, classEntity, slots);
    // }
}
