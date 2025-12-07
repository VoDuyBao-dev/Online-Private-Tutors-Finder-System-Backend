package com.example.tutorsFinderSystem.controller.auth;

import com.example.tutorsFinderSystem.dto.ApiResponse;
import com.example.tutorsFinderSystem.dto.request.TutorRegisterRequest;
import com.example.tutorsFinderSystem.dto.response.TutorRegisterResponse;
import com.example.tutorsFinderSystem.exceptions.AppException;
import com.example.tutorsFinderSystem.exceptions.ErrorCode;
import com.example.tutorsFinderSystem.services.TutorRegisterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/auth/tutors")
@RequiredArgsConstructor
// @CrossOrigin(origins = "*")
public class TutorRegisterController {

    private final TutorRegisterService tutorRegisterService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<TutorRegisterResponse>> registerTutor(

            @RequestParam("data") String json,

            @RequestPart("avatarFile") MultipartFile avatarFile,

            @RequestParam(value = "certificateNames", required = false)
            List<String> certificateNames,

            @RequestPart(value = "certificateFiles", required = false)
            List<MultipartFile> certificateFiles
    ) {

        try {
            // Parse JSON → DTO
            TutorRegisterRequest request = objectMapper.readValue(json, TutorRegisterRequest.class);

            request.setAvatarFile(avatarFile);
            request.setCertificateNames(certificateNames);
            request.setCertificateFiles(certificateFiles);

            // Validate dữ liệu
            Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
            Set<ConstraintViolation<TutorRegisterRequest>> violations = validator.validate(request);

            if (!violations.isEmpty()) {
                throw new AppException(ErrorCode.INVALID_FIELD);
            }

            TutorRegisterResponse result = tutorRegisterService.registerTutor(request);

            return ResponseEntity.ok(
                ApiResponse.<TutorRegisterResponse>builder()
                        .code(200)
                        .message("Đăng ký gia sư thành công")
                        .result(result)
                        .build()
            );

        } catch (AppException e) {
            return ResponseEntity.badRequest().body(
                ApiResponse.<TutorRegisterResponse>builder()
                        .code(e.getErrorCode().getCode())
                        .message(e.getMessage())
                        .result(null)
                        .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(
                ApiResponse.<TutorRegisterResponse>builder()
                        .code(500)
                        .message(e.getMessage())
                        .result(null)
                        .build()
            );
        }
    }
}
