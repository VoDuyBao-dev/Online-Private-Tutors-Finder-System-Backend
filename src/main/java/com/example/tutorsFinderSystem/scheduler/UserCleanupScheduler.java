package com.example.tutorsFinderSystem.scheduler;

import com.example.tutorsFinderSystem.entities.User;
import com.example.tutorsFinderSystem.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class UserCleanupScheduler {

    private final UserRepository userRepository;

    // Chạy mỗi 5 phút để xóa tài khoản chưa kích hoạt
    @Scheduled(fixedRate = 300000) // 5 phút = 300000 ms
    public void deleteExpiredAccounts() {
        List<User> expiredUsers = userRepository
                .findByEnabledFalseAndActivationExpiryTimeBefore(LocalDateTime.now());

        if (!expiredUsers.isEmpty()) {
            userRepository.deleteAll(expiredUsers);
            log.info("Đã xóa {} tài khoản chưa kích hoạt hết hạn", expiredUsers.size());
        }
    }
}
