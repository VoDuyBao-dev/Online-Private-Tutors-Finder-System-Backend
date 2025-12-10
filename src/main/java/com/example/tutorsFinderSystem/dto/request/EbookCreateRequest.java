package com.example.tutorsFinderSystem.dto.request;

import com.example.tutorsFinderSystem.enums.EbookType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EbookCreateRequest {

    @NotBlank(message = "EBOOK_TITLE_REQUIRED")
    @Size(max = 255, message = "EBOOK_TITLE_TOO_LONG")
    private String title;

    @NotNull(message = "EBOOK_TYPE_REQUIRED")
    private EbookType type;

    // @NotBlank(message = "EBOOK_FILE_PATH_REQUIRED")
    // @Size(max = 255, message = "EBOOK_FILE_PATH_TOO_LONG")
    // private String filePath;
}
