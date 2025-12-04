package com.example.tutorsFinderSystem.dto.request;

import com.example.tutorsFinderSystem.enums.EbookType;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EbookUpdateRequest {

    @Size(max = 255, message = "EBOOK_TITLE_TOO_LONG")
    private String title;

    private EbookType type;

    @Size(max = 255, message = "EBOOK_FILE_PATH_TOO_LONG")
    private String filePath;
}
