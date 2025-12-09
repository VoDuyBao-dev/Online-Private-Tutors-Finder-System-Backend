package com.example.tutorsFinderSystem.services;

import com.example.tutorsFinderSystem.dto.response.OtpResponse;
import com.example.tutorsFinderSystem.entities.OtpVerification;
import com.example.tutorsFinderSystem.exceptions.AppException;
import com.example.tutorsFinderSystem.exceptions.ErrorCode;
import com.example.tutorsFinderSystem.repositories.OtpVerificationRepository;
import com.example.tutorsFinderSystem.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Slf4j
@Service
public class OtpService {

    @Autowired
    private OtpVerificationRepository otpVerificationRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;


    private String generateOtp() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    // Tạo và gửi OTP
    public void generateAndSendOtp(String email) {
        String otp = generateOtp();

        OtpVerification otpVerification = OtpVerification.builder()
                .email(email)
                .otpCode(otp)
                .expiresAt(LocalDateTime.now().plusMinutes(1))
                .build();

        try{
            otpVerificationRepository.save(otpVerification);
        }catch (Exception e){
            log.error("Lỗi khi lưu OTP: ",e);
            throw new AppException(ErrorCode.OTP_SAVE_FAILED, e);
        }


        emailService.sendOtpEmail(email, otp);


    }

    //    ktra otp
    public OtpResponse verifyOtp(String email, String otp) {
        log.info("Verifying OTP: email={}, otp={}", email, otp);
        OtpVerification otpVerification;

        try{
            otpVerification = otpVerificationRepository.findTopByEmailOrderByCreatedAtDesc(email);
            log.info("otpVerification: {}", otpVerification);
        }catch (Exception e){
            throw new AppException(ErrorCode.OTP_NOT_FOUND);
        }

        if(otpVerification == null){
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }else if (otpVerification.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new AppException(ErrorCode.OTP_EXPIRED);
        } else if (otpVerification.isUsed() || !otpVerification.getOtpCode().equals(otp)) {
            throw new AppException(ErrorCode.INVALID_OTP);
        }

        otpVerification.setUsed(true);
        otpVerificationRepository.save(otpVerification);
        OtpResponse otpResponse = OtpResponse.builder()
                .email(email)
                .status(true)
                .build();
        return otpResponse;
    }




}

