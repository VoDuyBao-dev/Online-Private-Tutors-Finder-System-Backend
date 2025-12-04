package com.example.tutorsFinderSystem.controller.tutor;

import com.example.tutorsFinderSystem.dto.ApiResponse;
import com.example.tutorsFinderSystem.dto.request.TutorPersonalInfoUpdateRequest;
import com.example.tutorsFinderSystem.dto.request.TutorSubjectsUpdateRequest;
import com.example.tutorsFinderSystem.dto.response.TutorAvatarUpdateResponse;
import com.example.tutorsFinderSystem.dto.response.TutorEducationResponse;
import com.example.tutorsFinderSystem.dto.response.TutorPersonalInfoResponse;
import com.example.tutorsFinderSystem.dto.response.TutorRatingsSummaryResponse;
import com.example.tutorsFinderSystem.dto.response.TutorSubjectsResponse;
import com.example.tutorsFinderSystem.exceptions.AppException;
import com.example.tutorsFinderSystem.exceptions.ErrorCode;
import com.example.tutorsFinderSystem.dto.request.TutorEducationUpdateRequest;
import com.example.tutorsFinderSystem.services.TutorProfileService;
import jakarta.validation.Valid;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@RestController
@RequestMapping("/tutors/profile")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('SCOPE_TUTOR')")
public class TutorProfileController {

    private final TutorProfileService tutorProfileService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 1. PERSONAL INFO
    @GetMapping("/personal-info")
    public ApiResponse<TutorPersonalInfoResponse> getPersonalInfo() {
        return ApiResponse.<TutorPersonalInfoResponse>builder()
                .code(200)
                .message("Lấy thông tin cá nhân thành công")
                .result(tutorProfileService.getPersonalInfo())
                .build();
    }

    @PutMapping("/personal-info")
    public ApiResponse<TutorPersonalInfoResponse> updatePersonalInfo(
            @Valid @RequestBody TutorPersonalInfoUpdateRequest req) {
        return ApiResponse.<TutorPersonalInfoResponse>builder()
                .code(200)
                .message("Cập nhật thông tin cá nhân thành công")
                .result(tutorProfileService.updatePersonalInfo(req))
                .build();
    }

    // 2. EDUCATION
    @GetMapping("/education")
    public ApiResponse<TutorEducationResponse> getEducation() {
        return ApiResponse.<TutorEducationResponse>builder()
                .code(200)
                .message("Lấy thông tin học vấn thành công")
                .result(tutorProfileService.getEducation())
                .build();
    }

    @PutMapping(value = "/education", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<TutorEducationResponse> updateEducation(
            @RequestParam("data") String json, // TEXT (JSON)
            @RequestPart("proofFile") MultipartFile proofFile // FILE
    ) {
        try {
            // Parse JSON → DTO
            TutorEducationUpdateRequest request = objectMapper.readValue(json, TutorEducationUpdateRequest.class);

            // gán file vào DTO
            request.setProofFile(proofFile);

            // VALIDATION 
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            var violations = validator.validate(request);

            if (!violations.isEmpty()) {
                throw new AppException(ErrorCode.INVALID_FIELD);
            }

            var result = tutorProfileService.updateEducation(request);

            return ApiResponse.<TutorEducationResponse>builder()
                    .code(200)
                    .message("Cập nhật học vấn thành công")
                    .result(result)
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.<TutorEducationResponse>builder()
                    .code(500)
                    .message(e.getMessage())
                    .result(null)
                    .build();
        }
    }

    // 3. SUBJECTS
    @GetMapping("/subjects")
    public ApiResponse<TutorSubjectsResponse> getSubjects() {
        return ApiResponse.<TutorSubjectsResponse>builder()
                .code(200)
                .message("Lấy danh sách môn dạy thành công")
                .result(tutorProfileService.getSubjects())
                .build();
    }

    @PutMapping("/subjects")
    public ApiResponse<TutorSubjectsResponse> updateSubjects(@Valid @RequestBody TutorSubjectsUpdateRequest req) {
        return ApiResponse.<TutorSubjectsResponse>builder()
                .code(200)
                .message("Cập nhật môn dạy thành công")
                .result(tutorProfileService.updateSubjects(req))
                .build();
    }

    // 4. RATINGS SUMMARY
    @GetMapping("/ratings")
    public ApiResponse<TutorRatingsSummaryResponse> getRatingSummary() {
        return ApiResponse.<TutorRatingsSummaryResponse>builder()
                .code(200)
                .message("Lấy thông tin đánh giá thành công")
                .result(tutorProfileService.getRatingsSummary())
                .build();
    }

    // 5. AVATAR UPDATE
    @PutMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<TutorAvatarUpdateResponse> updateAvatar(
            @RequestPart("avatarFile") MultipartFile file) {
        return ApiResponse.<TutorAvatarUpdateResponse>builder()
                .code(200)
                .message("Cập nhật avatar thành công")
                .result(tutorProfileService.updateAvatar(file))
                .build();
    }

}
