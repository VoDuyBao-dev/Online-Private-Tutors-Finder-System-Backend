package com.example.tutorsFinderSystem.entities;

import com.example.tutorsFinderSystem.enums.EbookType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ebooks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ebook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ebook_id")
    private Long ebookId;

    @Column(name = "title", length = 255)
    private String title; // tên tài liệu

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 50)
    private EbookType type; // sách giáo khoa, tài liệu, đề thi tham khảo

    @Column(name = "file_path", length = 255)
    private String filePath; // đường dẫn file trên server

    @ManyToOne
    @JoinColumn(name = "uploaded_by")
    private User uploadedBy; // người tải lên (Admin hoặc Tutor)

    @Builder.Default
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
