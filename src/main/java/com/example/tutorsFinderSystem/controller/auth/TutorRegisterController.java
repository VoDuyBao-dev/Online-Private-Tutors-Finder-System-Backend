package com.example.tutorsFinderSystem.controller.auth;

import com.example.tutorsFinderSystem.dto.ApiResponse;
import com.example.tutorsFinderSystem.dto.request.TutorRegisterRequest;
import com.example.tutorsFinderSystem.dto.response.TutorRegisterResponse;
import com.example.tutorsFinderSystem.services.TutorRegisterService;
import com.example.tutorsFinderSystem.exceptions.AppException;
import com.example.tutorsFinderSystem.exceptions.ErrorCode;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@RestController
@RequestMapping("auth/tutors")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TutorRegisterController {

    private final TutorRegisterService tutorRegisterService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<TutorRegisterResponse>> registerTutor(
            // JSON gửi dạng TEXT
            @RequestParam("data") String json,
            // File bắt buộc
            @RequestPart("avatarFile") MultipartFile avatarFile,
            @RequestPart("proofFile") MultipartFile proofFile
    ) {

        try {
            // System.out.println("Avatar: " + (avatarFile != null ? avatarFile.getOriginalFilename() : "null"));
            // System.out.println("Proof: " + (proofFile != null ? proofFile.getOriginalFilename() : "null"));
            // System.out.println("Raw JSON: " + json);

            //  Parse JSON → Object
            TutorRegisterRequest request = objectMapper.readValue(json, TutorRegisterRequest.class);

            //  Gán file vào DTO
            request.setAvatarFile(avatarFile);
            request.setProofFile(proofFile);

            // VALIDATION (tương đương @Valid)
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<TutorRegisterRequest>> violations = validator.validate(request);

            if (!violations.isEmpty()) {
                String message = violations.iterator().next().getMessage();
                System.out.println(message);
                throw new AppException(ErrorCode.INVALID_FIELD);
            }

            // Gọi service
            TutorRegisterResponse result = tutorRegisterService.registerTutor(request);

            // Trả API chuẩn
            ApiResponse<TutorRegisterResponse> response = ApiResponse.<TutorRegisterResponse>builder()
                    .code(200)
                    .message("Đăng ký gia sư thành công")
                    .result(result)
                    .build();

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();

            ApiResponse<TutorRegisterResponse> error = ApiResponse.<TutorRegisterResponse>builder()
                    .code(500)
                    .message(e.getMessage())
                    .result(null)
                    .build();

            return ResponseEntity.badRequest().body(error);
        }
    }
}
