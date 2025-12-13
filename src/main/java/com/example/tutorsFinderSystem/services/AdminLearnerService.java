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
import com.example.tutorsFinderSystem.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminLearnerService {

    private final LearnerRepository learnerRepository;
    private final AdminLearnerMapper adminLearnerMapper;
    private final UserRepository userRepository;

    private User getCurrentAdmin() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();

    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

    

    if (!user.getRoles().contains(Role.ADMIN.name())) {
        throw new AppException(ErrorCode.UNAUTHORIZED);
    }

    return user;
}


    // 1) Danh sách phân trang
    @Transactional
    public PageResponse<AdminLearnerSummaryResponse> getLearners(int page, int size) {

        getCurrentAdmin();

        Pageable pageable = PageRequest.of(page, size, Sort.by("user.createdAt").descending());

        Page<Learner> learnerPage = learnerRepository.findAllLearnerPageable("LEARNER",pageable);

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

        Learner learner = learnerRepository.findByUserUserId(learnerId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        User user = learner.getUser();
        if (user == null) {
            throw new AppException(ErrorCode.TUTOR_USER_NOT_FOUND);
        }

        if (!user.getRoles().contains(Role.LEARNER.name()))
            throw new AppException(ErrorCode.USER_IS_NOT_LEARNER);

        return adminLearnerMapper.toDetail(learner);
    }

    // 3) Cập nhật trạng thái ACTIVE ↔ INACTIVE
    @Transactional
    public AdminLearnerStatusUpdateResponse toggleStatus(Long learnerId) {

        Learner learner = learnerRepository.findByUserUserId(learnerId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        User user = learner.getUser();
        if (user == null) {
            throw new AppException(ErrorCode.TUTOR_USER_NOT_FOUND);
        }

        if (!user.getRoles().contains(Role.LEARNER.name()))
            throw new AppException(ErrorCode.USER_IS_NOT_LEARNER);

        UserStatus current = user.getStatus();
        UserStatus next = (current == UserStatus.ACTIVE) ? UserStatus.INACTIVE : UserStatus.ACTIVE;

        user.setStatus(next);
        learnerRepository.save(learner);

        return new AdminLearnerStatusUpdateResponse(learnerId, next);
    }

    @Transactional
    public AdminLearnerStatsResponse getLearnerStats() {

        long total = learnerRepository.countAllLearners("LEARNER");
        long active = learnerRepository.countActiveLearners("LEARNER");
        long inactive = learnerRepository.countInactiveLearners("LEARNER");

        return AdminLearnerStatsResponse.builder()
                .total(total)
                .active(active)
                .inactive(inactive)
                .build();
    }

}
