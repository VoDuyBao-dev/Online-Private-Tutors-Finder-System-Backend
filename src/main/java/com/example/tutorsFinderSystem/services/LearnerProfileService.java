package com.example.tutorsFinderSystem.services;

import com.example.tutorsFinderSystem.dto.request.LearnerProfileUpdateRequest;
import com.example.tutorsFinderSystem.dto.response.LearnerProfileResponse;
import com.example.tutorsFinderSystem.entities.Learner;
import com.example.tutorsFinderSystem.entities.User;
import com.example.tutorsFinderSystem.exceptions.AppException;
import com.example.tutorsFinderSystem.exceptions.ErrorCode;
import com.example.tutorsFinderSystem.mapper.LearnerProfileMapper;
import com.example.tutorsFinderSystem.repositories.LearnerRepository;
import com.example.tutorsFinderSystem.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LearnerProfileService {

    private final UserRepository userRepository;
    private final LearnerRepository learnerRepository;
    private final LearnerProfileMapper learnerProfileMapper;
    private final UserService userService;

    private Learner getLearner(User user) {
        return learnerRepository.findByUserUserId(user.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.LEARNER_NOT_FOUND));
    }

    public LearnerProfileResponse updateProfile(LearnerProfileUpdateRequest request) {
        User user = userService.getCurrentUser();
        Learner learner = getLearner(user);

        // Update user
        user.setFullName(request.getFullName());
        user.setPhoneNumber(request.getPhoneNumber());

        // Update learner
        learner.setAddress(request.getAddress());

        userRepository.save(user);
        learnerRepository.save(learner);

        return learnerProfileMapper.toResponse(learner, user);
    }

    public LearnerProfileResponse getProfile() {

        User user = userService.getCurrentUser();
        Learner learner = getLearner(user);

        return learnerProfileMapper.toResponse(learner, user);
    }
}
