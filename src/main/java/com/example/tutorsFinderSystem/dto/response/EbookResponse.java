package com.example.tutorsFinderSystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EbookResponse {

    private Long ebookId;
    private String title;
    private String type;
    private String filePath;

    private Long uploadedById;
    private String uploadedByName;

    private LocalDateTime createdAt;
}
