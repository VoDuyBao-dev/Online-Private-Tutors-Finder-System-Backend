package com.example.tutorsFinderSystem.mapper;

import com.example.tutorsFinderSystem.dto.response.AdminProfileResponse;
import com.example.tutorsFinderSystem.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface AdminProfileMapper {

    DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Mapping(
        target = "role",
        expression = "java(extractMainRole(user))"
    )
    @Mapping(
        target = "roleLabel",
        expression = "java(buildRoleLabel(user))"
    )
    @Mapping(
        target = "createdAt",
        expression = "java(user.getCreatedAt() != null ? user.getCreatedAt().format(FORMATTER) : null)"
    )
    @Mapping(
        target = "avatarUrl",
        source = "avatarImage"
    )
    AdminProfileResponse toResponse(User user);

    // ===== helper methods =====

    default String extractMainRole(User user) {
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            return null;
        }
        return user.getRoles().iterator().next(); // ADMIN
    }

    default String buildRoleLabel(User user) {
        String role = extractMainRole(user);
        if ("ADMIN".equals(role)) return "Quản trị viên";
        if ("TUTOR".equals(role)) return "Gia sư";
        if ("PARENT".equals(role)) return "Người học";
        return role;
    }
}
