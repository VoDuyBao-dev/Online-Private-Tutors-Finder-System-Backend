package com.example.tutorsFinderSystem.dto.request;

import jakarta.validation.constraints.*;

// import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TutorEducationUpdateRequest {

    @NotBlank(message = "UNIVERSITY_REQUIRED")
    @Size(max = 255, message = "UNIVERSITY_TOO_LONG")
    private String university;

    @NotBlank(message = "INTRO_REQUIRED")
    private String introduction;

    @NotNull(message = "PRICE_REQUIRED")
    @Min(value = 10000, message = "PRICE_TOO_LOW")
    private Integer pricePerHour;

    // Danh sách chứng chỉ (tên file / URL)
    // @NotEmpty(message = "CERTIFICATES_REQUIRED")
    // private List<@NotBlank(message = "CERTIFICATE_ITEM_REQUIRED") String> certificates;

    // File/bằng cấp chính đã upload (PDF) – map với proofFileUrl trong entity
    @NotNull(message = "PROOF_FILE_REQUIRED")
    private MultipartFile proofFile;
}
