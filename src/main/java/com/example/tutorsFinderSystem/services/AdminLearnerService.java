package com.example.tutorsFinderSystem.services;

import com.example.tutorsFinderSystem.dto.PageResponse;
import com.example.tutorsFinderSystem.dto.response.*;
import com.example.tutorsFinderSystem.entities.Learner;
import com.example.tutorsFinderSystem.entities.User;
import com.example.tutorsFinderSystem.enums.Role;
import com.example.tutorsFinderSystem.enums.UserStatus;
import com.example.tutorsFinderSystem.exceptions.AppException;
import com.example.tutorsFinderSystem.exceptions.ErrorCode;
import com.example.tutorsFinderSystem.mapper.AdminLearnerMapper;
import com.example.tutorsFinderSystem.repositories.LearnerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminLearnerService {

    private final LearnerRepository learnerRepository;
    private final AdminLearnerMapper adminLearnerMapper;

    // 1) Danh sách phân trang
    @Transactional
    public PageResponse<AdminLearnerSummaryResponse> getLearners(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("user.createdAt").descending());

        Page<Learner> learnerPage = learnerRepository.findAllLearnerPageable(pageable);

        var items = learnerPage.getContent().stream()
                .map(adminLearnerMapper::toSummary)
                .toList();

        return PageResponse.<AdminLearnerSummaryResponse>builder()
                .items(items)
                .page(page)
                .size(size)
                .totalItems(learnerPage.getTotalElements())
                .totalPages(learnerPage.getTotalPages())
                .build();
    }

    // 2) Chi tiết learner
    @Transactional
    public AdminLearnerDetailResponse getLearnerDetail(Long learnerId) {

        Learner learner = learnerRepository.findById(learnerId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        User user = learner.getUser();
        if (user == null) {
            throw new AppException(ErrorCode.TUTOR_USER_NOT_FOUND);
        }

        if (user.getRole() != Role.LEARNER)
            throw new AppException(ErrorCode.USER_IS_NOT_LEARNER);

        return adminLearnerMapper.toDetail(learner);
    }

    // 3) Cập nhật trạng thái ACTIVE ↔ INACTIVE
    @Transactional
    public AdminLearnerStatusUpdateResponse toggleStatus(Long learnerId) {

        Learner learner = learnerRepository.findById(learnerId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        User user = learner.getUser();
        if (user == null) {
            throw new AppException(ErrorCode.TUTOR_USER_NOT_FOUND);
        }

        if (user.getRole() != Role.LEARNER)
            throw new AppException(ErrorCode.USER_IS_NOT_LEARNER);

        UserStatus current = user.getStatus();
        UserStatus next = (current == UserStatus.ACTIVE) ? UserStatus.INACTIVE : UserStatus.ACTIVE;

        user.setStatus(next);
        learnerRepository.save(learner);

        return new AdminLearnerStatusUpdateResponse(learnerId, next);
    }

    @Transactional
    public AdminLearnerStatsResponse getLearnerStats() {

        long total = learnerRepository.countAllLearners();
        long active = learnerRepository.countActiveLearners();
        long inactive = learnerRepository.countInactiveLearners();

        return AdminLearnerStatsResponse.builder()
                .total(total)
                .active(active)
                .inactive(inactive)
                .build();
    }

}
