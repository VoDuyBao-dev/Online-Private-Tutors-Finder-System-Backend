package com.example.tutorsFinderSystem.controller.auth;

import com.example.tutorsFinderSystem.dto.ApiResponse;
import com.example.tutorsFinderSystem.dto.request.*;
import com.example.tutorsFinderSystem.dto.response.AuthenticationResponse;
import com.example.tutorsFinderSystem.dto.response.IntrospectResponse;
import com.example.tutorsFinderSystem.dto.response.LearnerResponse;
import com.example.tutorsFinderSystem.dto.response.UserResponse;
import com.example.tutorsFinderSystem.services.AuthenticationService;
import com.example.tutorsFinderSystem.services.OtpService;
import com.example.tutorsFinderSystem.services.UserService;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    UserService userService;
    OtpService otpService;
    AuthenticationService authenticationService;

//    register leaner
    @PostMapping("/register/learner")
    public ApiResponse<LearnerResponse> registerUser(@Valid @RequestBody LearnerRequest learnerRequest) {
        log.info("userRequest: {}", learnerRequest);

        LearnerResponse learnerResponse = userService.createLearner(learnerRequest);
        otpService.generateAndSendOtp(learnerRequest.getEmail());
        return ApiResponse.<LearnerResponse>builder()
                .code(200)
                .message("Đăng ký thành công. Vui lòng kiểm tra email để xác thực tài khoản.")
                .result(learnerResponse)
                .build();


    }

//    TOÀN BỘ HÀM Ở CLASS NÀY ĐỀU LÀ TEST API CÓ THỂ GIỮ CODE VÀ TRIỂN KHAI CHI TIẾT HOẶC XÓA ĐI CŨNG ĐƯỢC
//    Đây là test đăng nhập rồi trả token chứ chưa có triển khai rõ ràng nha Lan
    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .code(200)
                .message("Đăng nhập thành công")
                .result(authenticationResponse)
                .build();

    }

//
    @GetMapping("/introspect")
    public  ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        IntrospectResponse introspectResponse = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .code(200)
                .result(introspectResponse)
                .build();
    }

    @PostMapping("/refresh-token")
    public ApiResponse<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest request) throws ParseException, JOSEException {
        AuthenticationResponse authenticationResponse = authenticationService.refreshToken(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .code(200)
                .message("Làm mới token thành công")
                .result(authenticationResponse)
                .build();
    }

    @PostMapping("/logout")
    public  ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Đăng xuất thành công")
                .build();
    }
}
