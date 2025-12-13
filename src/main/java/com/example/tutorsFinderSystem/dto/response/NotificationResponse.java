package com.example.tutorsFinderSystem.dto.response;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {
    private Long notificationId;
    private String type;
    private String title;
    private String content;
    private Boolean isRead;
    private String createdAt; // format string để FE dễ dùng
}
