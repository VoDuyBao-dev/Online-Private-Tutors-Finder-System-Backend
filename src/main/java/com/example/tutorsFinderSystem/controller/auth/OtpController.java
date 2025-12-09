package com.example.tutorsFinderSystem.controller.auth;

import com.example.tutorsFinderSystem.dto.ApiResponse;
import com.example.tutorsFinderSystem.dto.request.OtpRequest;
import com.example.tutorsFinderSystem.dto.response.OtpResponse;
import com.example.tutorsFinderSystem.exceptions.AppException;
import com.example.tutorsFinderSystem.exceptions.ErrorCode;
import com.example.tutorsFinderSystem.services.OtpService;
import com.example.tutorsFinderSystem.services.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/otp")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OtpController {
    OtpService otpService;
    UserService userService;

    //    xác thực otp
    @PostMapping("/verifyOtp")
    public ApiResponse<OtpResponse> verifyOTP(@RequestBody OtpRequest otpRequest) {
        OtpResponse otpResponse = otpService.verifyOtp(otpRequest.getEmail(), otpRequest.getOtpCode());

        if(otpRequest.getOtpType() == null){
            throw new AppException(ErrorCode.OTP_TYPE_REQUIRED);
        }
        switch (otpRequest.getOtpType()) {
            case ACCOUNT_ACTIVATION -> userService.activateUser(otpRequest.getEmail());
            case FORGOT_PASSWORD -> {}
            default -> throw new AppException(ErrorCode.INVALID_OTP_TYPE);
        }
        return ApiResponse.<OtpResponse>builder()
                .code(200)
                .message("xac thuc otp thanh cong")
                .result(otpResponse)
                .build();
    }

    @PostMapping("/resendOtp")
    public ApiResponse<Void> resendOtp(@Valid @RequestBody OtpRequest otpRequest) {
        userService.checkUserExist(otpRequest.getEmail());
        otpService.generateAndSendOtp(otpRequest.getEmail());
        return ApiResponse.<Void>builder()
                .code(200)
                .message("gui lai otp thanh cong")
                .build();
    }
}
