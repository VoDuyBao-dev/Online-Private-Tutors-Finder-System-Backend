package com.example.tutorsFinderSystem.services;

import com.example.tutorsFinderSystem.dto.request.LearnerRequest;
import com.example.tutorsFinderSystem.dto.response.LearnerResponse;
import com.example.tutorsFinderSystem.dto.response.UserResponse;
import com.example.tutorsFinderSystem.entities.Learner;
import com.example.tutorsFinderSystem.entities.User;
import com.example.tutorsFinderSystem.enums.UserStatus;
import com.example.tutorsFinderSystem.exceptions.AppException;
import com.example.tutorsFinderSystem.exceptions.ErrorCode;
import com.example.tutorsFinderSystem.mapper.LearnerMapper;
import com.example.tutorsFinderSystem.mapper.UserMapper;
import com.example.tutorsFinderSystem.repositories.LearnerRepository;
import com.example.tutorsFinderSystem.repositories.UserRepository;
import com.example.tutorsFinderSystem.enums.Role;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

     PasswordEncoder passwordEncoder;

    UserRepository userRepository;
    UserMapper userMapper;
    LearnerMapper learnerMapper;
    LearnerRepository learnerRepository;


    private User createUser(LearnerRequest learnerRequest) {

        if (!learnerRequest.getPasswordHash().equals(learnerRequest.getConfirmPassword())) {
            throw new AppException(ErrorCode.PASSWORDS_DO_NOT_MATCH);
        }
        if (userRepository.existsByEmail(learnerRequest.getEmail())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(learnerRequest);

        user.setEmail(learnerRequest.getEmail());
        user.setStatus(UserStatus.PENDING);
        user.setEnabled(false);
        user.setPasswordHash(passwordEncoder.encode(learnerRequest.getPasswordHash()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.LEARNER.name());
        user.setRoles(roles);

        log.info("user in createUser{}", user);

        User savedUser=userRepository.save(user);

        return savedUser;
    }

    public LearnerResponse createLearner(LearnerRequest learnerRequest) {
        User user = createUser(learnerRequest);
        Learner learner = Learner.builder()
                .user(user)
                .fullName(user.getFullName())
                .build();
        log.info("learner in createLearner{}", learner);

        return learnerMapper.toResponse(learnerRepository.save(learner));

    }

//    Check user đã kích hoạt tài khoản chưa và các trạng thái khác...
    public void validateUserStatus(User user) {
        switch (user.getStatus()) {

            case PENDING ->
                    throw new AppException(ErrorCode.ACCOUNT_NOT_ACTIVATED);

            case LOCKED ->
                    throw new AppException(ErrorCode.ACCOUNT_LOCKED);

            case INACTIVE ->
                    throw new AppException(ErrorCode.ACCOUNT_INACTIVE);

            case ACTIVE -> {

            }

            default ->
                    throw new AppException(ErrorCode.UNKNOWN_USER_STATUS);

        }

        if (Boolean.FALSE.equals(user.getEnabled())) {
            throw new AppException(ErrorCode.ACCOUNT_DISABLED);
        }
    }

//    // kích hoạt tài khoản
    public void activateUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        log.info("user in activateUser{}", user);

        user.setEnabled(true);
        user.setStatus(UserStatus.ACTIVE);
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new AppException(ErrorCode.UPDATE_USER_FAILED, e);
        }

    }

    // Kiểm tra email người dùng đang đăng nhập. 
    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

}
