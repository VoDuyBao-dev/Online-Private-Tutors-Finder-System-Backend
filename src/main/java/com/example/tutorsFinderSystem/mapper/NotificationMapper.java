package com.example.tutorsFinderSystem.mapper;

import java.util.List;

import org.mapstruct.*;

import com.example.tutorsFinderSystem.dto.response.NotificationResponse;
import com.example.tutorsFinderSystem.entities.Notification;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(target = "notificationId", source = "notificationId")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "isRead", source = "isRead")
    @Mapping(target = "createdAt", expression = "java(n.getCreatedAt() == null ? null : n.getCreatedAt().toString())")
    NotificationResponse toResponse(Notification n);

    List<NotificationResponse> toResponses(List<Notification> list);
}

