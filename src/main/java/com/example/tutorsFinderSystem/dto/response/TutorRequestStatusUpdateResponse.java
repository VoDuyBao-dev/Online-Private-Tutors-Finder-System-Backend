package com.example.tutorsFinderSystem.dto.response;

import com.example.tutorsFinderSystem.enums.ClassRequestStatus;
import com.example.tutorsFinderSystem.enums.ClassStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TutorRequestStatusUpdateResponse {

    private Long requestId;
    private Long classId;

    private ClassRequestStatus requestStatus;
    private ClassStatus classStatus;
}
