package com.example.tutorsFinderSystem.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminLearnerStatsResponse {
    private long total; // tổng số learner
    private long active; // learner ACTIVE
    private long inactive; // learner INACTIVE
}
