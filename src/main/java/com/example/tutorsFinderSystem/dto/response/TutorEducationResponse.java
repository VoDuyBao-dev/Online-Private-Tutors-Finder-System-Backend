package com.example.tutorsFinderSystem.dto.response;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TutorEducationResponse {
    private String university;
    private String introduction;
    private Integer pricePerHour;
    private List<String> certificates;

    
}
