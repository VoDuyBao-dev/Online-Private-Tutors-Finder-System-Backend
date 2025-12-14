package com.example.tutorsFinderSystem.repositories;

import com.example.tutorsFinderSystem.entities.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    /**
     * Lấy lịch sử chat giữa 2 user (2 chiều)
     */
    @Query("""
        SELECT m FROM ChatMessage m
        WHERE (m.sender.userId = :userA AND m.receiver.userId = :userB)
           OR (m.sender.userId = :userB AND m.receiver.userId = :userA)
        ORDER BY m.sentAt DESC
    """)
    Page<ChatMessage> findChatHistory(
            Long userA,
            Long userB,
            Pageable pageable
    );

    /**
     * Lấy message mới nhất giữa current user và từng user khác
     */
    @Query("""
        SELECT m FROM ChatMessage m
        WHERE m.id IN (
            SELECT MAX(m2.id) FROM ChatMessage m2
            WHERE m2.sender.userId = :userId
               OR m2.receiver.userId = :userId
            GROUP BY
              CASE
                WHEN m2.sender.userId = :userId THEN m2.receiver.userId
                ELSE m2.sender.userId
              END
        )
        ORDER BY m.sentAt DESC
    """)
    List<ChatMessage> findLatestMessagesPerConversation(Long userId);

    /**
     * Đếm số tin chưa đọc
     */
    long countByReceiver_UserIdAndSender_UserIdAndIsReadFalse(
            Long receiverId,
            Long senderId
    );
}
