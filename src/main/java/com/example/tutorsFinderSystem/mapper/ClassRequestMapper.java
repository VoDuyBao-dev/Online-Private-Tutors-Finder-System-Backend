package com.example.tutorsFinderSystem.mapper;

import com.example.tutorsFinderSystem.dto.common.ClassRequestDTO;
import com.example.tutorsFinderSystem.entities.ClassRequest;
import com.example.tutorsFinderSystem.enums.ClassRequestStatus;
import com.example.tutorsFinderSystem.enums.ClassRequestType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClassRequestMapper {

    @Mapping(target = "requestId", source = "requestId")
    @Mapping(target = "type", expression = "java( convertTypeClassRequestToString(classRequest.getType()) )")
    @Mapping(target = "status", expression = "java( convertStatusClassRequestToString(classRequest.getStatus()) )")
    @Mapping(target = "subject", source = "subject.subjectName")
    @Mapping(target = "tutor", expression = "java(classRequest.getTutor() != null ? classRequest.getTutor().getUser().getFullName() : null)")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "additionalNotes", source = "additionalNotes")
    @Mapping(target = "createdAt", source = "createdAt")
    ClassRequestDTO toClassRequestDTO(ClassRequest classRequest);

    default String convertTypeClassRequestToString(ClassRequestType type) {
        switch (type) {
            case OFFICIAL:
                return "Học chính thức";
            case TRIAL:
                return "Học thử";
            default:
                return "Không xác định";
        }
    }

    default String convertStatusClassRequestToString(ClassRequestStatus status) {
        switch (status) {
            case PENDING:
                return "Đang chờ";
            case CONFIRMED:
                return "Đã xác nhận";
            case CANCELLED:
                return "Đã hủy";
            default:
                return "Không xác định";
        }

    }
}
