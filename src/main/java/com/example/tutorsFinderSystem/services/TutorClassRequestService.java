package com.example.tutorsFinderSystem.services;

import com.example.tutorsFinderSystem.dto.response.TutorRequestStatusUpdateResponse;
import com.example.tutorsFinderSystem.dto.response.tutorRequestClassResponse;
import com.example.tutorsFinderSystem.entities.CalendarClass;
import com.example.tutorsFinderSystem.entities.ClassEntity;
import com.example.tutorsFinderSystem.entities.ClassRequest;
import com.example.tutorsFinderSystem.enums.ClassRequestStatus;
import com.example.tutorsFinderSystem.enums.ClassRequestType;
import com.example.tutorsFinderSystem.enums.ClassStatus;
import com.example.tutorsFinderSystem.exceptions.AppException;
import com.example.tutorsFinderSystem.exceptions.ErrorCode;
import com.example.tutorsFinderSystem.mapper.TutorClassRequestMapper;
import com.example.tutorsFinderSystem.repositories.CalendarClassRepository;
import com.example.tutorsFinderSystem.repositories.ClassRepository;
import com.example.tutorsFinderSystem.repositories.ClassRequestRepository;
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

    /**
     * Lấy danh sách yêu cầu của 1 tutor với filter.
     */
    @Transactional(readOnly = true)
    public Page<tutorRequestClassResponse> getRequestsForTutor(
            Long tutorId,
            ClassRequestStatus status,
            ClassRequestType type,
            String keyword,
            int page,
            int size) {
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

        // Keyword filter (tên học viên hoặc tên môn học)
        List<tutorRequestClassResponse> content = requestPage.getContent().stream()
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

        return new PageImpl<>(content, pageable, requestPage.getTotalElements());
    }

    private boolean matchesKeyword(tutorRequestClassResponse dto, String keyword) {
        if (keyword == null || keyword.isBlank())
            return true;
        String kw = keyword.toLowerCase();
        return (dto.getFullName() != null && dto.getFullName().toLowerCase().contains(kw))
                || (dto.getSubjectName() != null && dto.getSubjectName().toLowerCase().contains(kw));
    }

    /**
     * Xem chi tiết 1 yêu cầu – đảm bảo thuộc về tutor.
     */
    // @Transactional(readOnly = true)
    // public TutorRequestDetailResponse getRequestDetail(Long tutorId, Long
    // requestId) {
    // ClassRequest request = classRequestRepository.findById(requestId)
    // .orElseThrow(() -> new AppException(ErrorCode.CLASS_REQUEST_NOT_FOUND));

    // if (!request.getTutor().getTutorId().equals(tutorId)) {
    // throw new AppException(ErrorCode.ACCESS_DENIED);
    // }

    // ClassEntity classEntity =
    // classRepository.findByClassRequest_RequestId(requestId)
    // .orElseThrow(() -> new AppException(ErrorCode.CLASS_NOT_FOUND));

    // List<CalendarClass> slots = calendarClassRepository
    // .findByClassRequest_RequestId(requestId);

    // return mapper.toDetail(request, classEntity, slots);
    // }

    /**
     * Tutor chấp nhận yêu cầu
     * - class_requests.status = CONFIRMED
     * - classes.status = ONGOING
     */
    @Transactional
    public TutorRequestStatusUpdateResponse acceptRequest(Long tutorId, Long requestId) {
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

        return TutorRequestStatusUpdateResponse.builder()
                .requestId(request.getRequestId())
                .classId(classEntity.getClassId())
                .requestStatus(request.getStatus())
                .classStatus(classEntity.getStatus())
                .build();
    }

    /**
     * Tutor từ chối yêu cầu
     * - class_requests.status = CANCELLED
     * - classes.status = CANCELLED
     */
    @Transactional
    public TutorRequestStatusUpdateResponse rejectRequest(Long tutorId, Long requestId) {
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
}
