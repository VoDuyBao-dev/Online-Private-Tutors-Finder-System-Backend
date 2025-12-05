package com.example.tutorsFinderSystem.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.*;

import com.example.tutorsFinderSystem.enums.UserStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminTutorSummaryResponse {
    private Long user_id;          // users.user_id
    private Long tutor_id;         // tutors.tutor_id
    private String full_name;      // users.full_name
    private UserStatus status;     // users.status
    private LocalDateTime updated_at; // users.updated_at (ngày tham gia dùng cột này)

    // Thông tin bổ sung
    private List<String> subjects;     // danh sách môn (subjects.subject_name)
    private Double average_rating;     // điểm trung bình đã làm tròn 1 chữ số

}
