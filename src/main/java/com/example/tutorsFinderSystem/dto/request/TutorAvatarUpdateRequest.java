package com.example.tutorsFinderSystem.dto.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TutorAvatarUpdateRequest {
    private MultipartFile avatarFile;
}
