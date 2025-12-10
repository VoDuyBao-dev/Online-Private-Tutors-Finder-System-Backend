package com.example.tutorsFinderSystem.dto.common;


import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificateDTO {
    private Long certificateId;
    private String certificateName;
    private String fileUrl;
    private LocalDateTime uploadedAt;
}
