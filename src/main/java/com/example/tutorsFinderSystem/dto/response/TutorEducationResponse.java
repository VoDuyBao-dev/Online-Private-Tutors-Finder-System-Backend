package com.example.tutorsFinderSystem.dto.response;

import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TutorEducationResponse {

    private Long tutorId;
    private String university;
    private String introduction;
    private Integer pricePerHour;

    private List<CertificateDTO> certificates;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CertificateDTO {
        private Long certificateId;
        private String certificateName;
        private Boolean approved;
        private List<FileDTO> files;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FileDTO {
        private Long fileId;
        private String fileUrl; // modified preview URL
        private String status;
        private Boolean isActive;
        private String uploadedAt;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TutorCertificateUpdateDTO {

        private Long certificateId; // ID chứng chỉ
        private String certificateName; // Tên mới (có thể sửa)
    }
}
