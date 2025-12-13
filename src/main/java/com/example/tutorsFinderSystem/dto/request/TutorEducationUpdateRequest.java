package com.example.tutorsFinderSystem.dto.request;

import jakarta.validation.constraints.*;

import java.util.List;

// import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import lombok.*;
import com.example.tutorsFinderSystem.dto.response.TutorEducationResponse.TutorCertificateUpdateDTO;

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

    private List<TutorCertificateUpdateDTO> certificates;
    private List<MultipartFile> certificateFiles;

}
