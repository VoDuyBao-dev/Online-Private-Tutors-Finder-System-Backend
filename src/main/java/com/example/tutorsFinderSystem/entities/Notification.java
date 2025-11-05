package com.example.tutorsFinderSystem.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // người nhận thông báo

    @Column(name = "type", nullable = false, length = 80)
    private String type;  
    // ví dụ: NEW_REQUEST, REQUEST_ACCEPTED, SESSION_REMINDER,...

    @Column(name = "title", nullable = false, length = 255)
    private String title;  // tiêu đề thông báo

    @Column(name = "content", length = 1000)
    private String content; // nội dung chi tiết

    @Builder.Default
    @Column(name = "is_read", nullable = false)
    private Boolean isRead = false; // đã đọc hay chưa

    @Builder.Default
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
