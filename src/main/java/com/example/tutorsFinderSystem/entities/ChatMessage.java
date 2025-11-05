package com.example.tutorsFinderSystem.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY trong SQL Server, AUTO_INCREMENT trong MySQL
    @Column(name = "message_id")
    private Long messageId;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender; // người gửi

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver; // người nhận

    @ManyToOne
    @JoinColumn(name = "sticker_id")
    private Sticker sticker; // sticker (nếu có)

    @Column(name = "content", columnDefinition = "TEXT")
    private String content; // nội dung tin nhắn (có thể null nếu chỉ gửi sticker)

    @Builder.Default
    @Column(name = "sent_at")
    private LocalDateTime sentAt = LocalDateTime.now();

    @Builder.Default
    @Column(name = "is_read")
    private Boolean isRead = false; // đã đọc hay chưa
}
