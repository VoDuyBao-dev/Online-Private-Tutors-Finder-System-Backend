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
import java.util.ArrayList;
// import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        // 1. Lấy tất cả lịch của tutor
        List<TutorAvailability> all = availabilityRepository.findByTutor_TutorIdOrderByStartTimeAsc(tutor.getTutorId());

        // 2. Chỉ giữ AVAILABLE + CANCELLED
        List<TutorAvailability> filtered = all.stream()
                .filter(a -> a.getStatus() == TutorAvailabilityStatus.AVAILABLE
                        || a.getStatus() == TutorAvailabilityStatus.CANCELLED)
                .toList();

        // 3. Gom nhóm theo "chu kỳ 3 tháng"
        Map<String, List<TutorAvailability>> grouped = filtered.stream()
                .collect(Collectors.groupingBy(a -> buildGroupKey(a)));

        List<TutorAvailabilityResponse> result = new ArrayList<>();

        // 4. Mỗi group -> 1 bản ghi đại diện
        for (List<TutorAvailability> group : grouped.values()) {

            // group đã sort theo startTime
            TutorAvailability first = group.get(0);
            TutorAvailability last = group.get(group.size() - 1);

            // String dayOfWeek = first.getStartTime().getDayOfWeek().name();

            TutorAvailabilityResponse response = availabilityMapper.toResponse(first);

            // thêm startDate & endDate của chu kỳ 3 tháng
            // response.setStartDate(
            // first.getStartTime().toLocalDate().toString());
            response.setEndDate(
                    last.getStartTime().toLocalDate().toString());

            result.add(response);
        }

        return result;
    }

    private String buildGroupKey(TutorAvailability a) {
        return a.getTutor().getTutorId() + "|"
                + a.getStartTime().getDayOfWeek().name() + "|"
                + a.getStartTime().toLocalTime() + "|"
                + a.getEndTime().toLocalTime();
    }

    // TẠO LỊCH RẢNH CHO 3 THÁNG
    // =============================
    @Transactional
    public TutorAvailabilityResponse createAvailability(TutorAvailabilityCreateRequest request) {

        Tutor tutor = getCurrentTutor();

        java.time.DayOfWeek targetDOW = java.time.DayOfWeek.valueOf(request.getDayOfWeek().name());

        LocalDate firstDate = getNextDateOfWeek(targetDOW);

        String[] range = request.getTimeRange().split("-");
        LocalTime start = LocalTime.parse(range[0].trim());
        LocalTime end = LocalTime.parse(range[1].trim());

        if (!end.isAfter(start)) {
            throw new AppException(ErrorCode.INVALID_TIME_RANGE);
        }

        if (request.getStatus() == TutorAvailabilityStatus.BOOKED) {
            throw new AppException(ErrorCode.INVALID_AVAILABILITY_STATUS);
        }

        LocalDateTime now = LocalDateTime.now();
        TutorAvailability firstSlot = null;

        // Tạo 12 tuần = 3 tháng
        for (int i = 0; i < 12; i++) {
            LocalDate date = firstDate.plusWeeks(i);
            LocalDateTime startTime = date.atTime(start);
            LocalDateTime endTime = date.atTime(end);

            boolean exists = availabilityRepository.existsByTutor_TutorIdAndStartTimeAndEndTime(
                    tutor.getTutorId(), startTime, endTime);

            if (exists)
                continue;

            TutorAvailability slot = TutorAvailability.builder()
                    .tutor(tutor)
                    .startTime(startTime)
                    .endTime(endTime)
                    .status(request.getStatus())
                    .createdAt(now)
                    .updatedAt(now)
                    .build();

            availabilityRepository.save(slot);

            if (firstSlot == null) {
                firstSlot = slot;
            }
        }

        if (firstSlot == null) {
            throw new AppException(ErrorCode.AVAILABILITY_CONFLICT);
        }

        // String dayOfWeek = targetDOW.name();
        // return availabilityMapper.toResponse(firstSlot);
        TutorAvailabilityResponse response = availabilityMapper.toResponse(firstSlot);

        // endDate = ngày cuối cùng trong chu kỳ 3 tháng
        LocalDate endDate = firstSlot.getStartTime()
                .toLocalDate()
                .plusMonths(3)
                .minusDays(1);

        response.setEndDate(endDate.toString());

        return response;

    }

    // SỬA LỊCH — SỬA TOÀN BỘ 3 THÁNG
    @Transactional
    public TutorAvailabilityResponse updateAvailability(Long id, TutorAvailabilityUpdateRequest request) {

        Tutor tutor = getCurrentTutor();

        TutorAvailability oldSlot = availabilityRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TUTOR_AVAILABILITY_NOT_FOUND));

        if (!oldSlot.getTutor().getTutorId().equals(tutor.getTutorId())) {
            throw new AppException(ErrorCode.TUTOR_AVAILABILITY_NOT_FOUND);
        }

        // ===== LẤY ĐẶC ĐIỂM NHẬN DIỆN NHÓM =====
        LocalTime oldStart = oldSlot.getStartTime().toLocalTime();
        LocalTime oldEnd = oldSlot.getEndTime().toLocalTime();
        java.time.DayOfWeek oldDayOfWeek = oldSlot.getStartTime().getDayOfWeek();

        // ===== TÍNH GIÁ TRỊ MỚI =====
        String[] range = request.getTimeRange().split("-");
        LocalTime newStart = LocalTime.parse(range[0].trim());
        LocalTime newEnd = LocalTime.parse(range[1].trim());

        if (!newEnd.isAfter(newStart)) {
            throw new AppException(ErrorCode.INVALID_TIME_RANGE);
        }

        // ===== LẤY TẤT CẢ LỊCH CỦA TUTOR =====
        List<TutorAvailability> list = availabilityRepository
                .findByTutor_TutorIdOrderByStartTimeAsc(tutor.getTutorId());

        LocalDateTime now = LocalDateTime.now();
        TutorAvailability firstUpdated = null;

        // ===== LẶP QUA TẤT CẢ ĐỂ SỬA 12 BẢN GHI =====
        for (TutorAvailability slot : list) {

            LocalDateTime sStart = slot.getStartTime();
            LocalDateTime sEnd = slot.getEndTime();

            if (!sStart.toLocalTime().equals(oldStart))
                continue;
            if (!sEnd.toLocalTime().equals(oldEnd))
                continue;
            if (!sStart.getDayOfWeek().equals(oldDayOfWeek))
                continue;

            // ===== GÁN DỮ LIỆU MỚI =====
            LocalDate date = slot.getStartTime().toLocalDate();

            slot.setStartTime(date.atTime(newStart));
            slot.setEndTime(date.atTime(newEnd));
            slot.setStatus(request.getStatus());
            slot.setUpdatedAt(now);

            availabilityRepository.save(slot);

            if (firstUpdated == null)
                firstUpdated = slot;
        }

        if (firstUpdated == null) {
            throw new AppException(ErrorCode.TUTOR_AVAILABILITY_NOT_FOUND);
        }

        TutorAvailabilityResponse response = availabilityMapper.toResponse(firstUpdated);

        // endDate = ngày cuối cùng trong chu kỳ 3 tháng
        LocalDate endDate = firstUpdated.getStartTime()
                .toLocalDate()
                .plusMonths(3)
                .minusDays(1);

        response.setEndDate(endDate.toString());

        return response;

    }

    // TÌM THỨ TIẾP THEO DỰA VÀO DOW
    private LocalDate getNextDateOfWeek(java.time.DayOfWeek dow) {
        LocalDate today = LocalDate.now();
        int diff = dow.getValue() - today.getDayOfWeek().getValue();
        if (diff < 0)
            diff += 7;
        return today.plusDays(diff);
    }

    @Transactional
    public void deleteAvailability(Long id) {

        Tutor tutor = getCurrentTutor();

        TutorAvailability oldSlot = availabilityRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TUTOR_AVAILABILITY_NOT_FOUND));

        if (!oldSlot.getTutor().getTutorId().equals(tutor.getTutorId())) {
            throw new AppException(ErrorCode.TUTOR_AVAILABILITY_NOT_FOUND);
        }

        // ===== KEY NHẬN DIỆN NHÓM =====
        LocalTime oldStart = oldSlot.getStartTime().toLocalTime();
        LocalTime oldEnd = oldSlot.getEndTime().toLocalTime();
        java.time.DayOfWeek oldDOW = oldSlot.getStartTime().getDayOfWeek();

        List<TutorAvailability> list = availabilityRepository
                .findByTutor_TutorIdOrderByStartTimeAsc(tutor.getTutorId());

        for (TutorAvailability slot : list) {

            if (!slot.getStartTime().toLocalTime().equals(oldStart))
                continue;
            if (!slot.getEndTime().toLocalTime().equals(oldEnd))
                continue;
            if (!slot.getStartTime().getDayOfWeek().equals(oldDOW))
                continue;

            availabilityRepository.delete(slot);
        }
    }

}
