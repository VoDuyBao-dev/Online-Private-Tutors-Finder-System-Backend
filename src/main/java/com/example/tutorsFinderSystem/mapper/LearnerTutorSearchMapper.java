package com.example.tutorsFinderSystem.mapper;

import com.example.tutorsFinderSystem.dto.response.TutorSearchItemResponse;
import com.example.tutorsFinderSystem.entities.Subject;
import com.example.tutorsFinderSystem.entities.Tutor;
import com.example.tutorsFinderSystem.entities.User;

import java.util.Collection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LearnerTutorSearchMapper {

    @Mapping(source = "tutorId", target = "tutorId")
    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "user.fullName", target = "fullName")
    @Mapping(target = "avatarUrl", expression = "java(buildAvatarUrl(tutor.getUser()))")
    @Mapping(target = "subjects", expression = "java(mapSubjectNames(tutor.getSubjects()))")
    TutorSearchItemResponse toItem(Tutor tutor);

    List<TutorSearchItemResponse> toItems(List<Tutor> tutors);


default List<String> mapSubjectNames(Collection<Subject> subjects) {
    if (subjects == null || subjects.isEmpty())
        return List.of();

    return subjects.stream()
            .map(Subject::getSubjectName)
            .toList();
}

    default String buildAvatarUrl(User user) {
        if (user == null || user.getAvatarImage() == null)
            return null;

        String avatar = user.getAvatarImage();

        if (!avatar.contains("http")) {
            return "http://localhost:8080/tutorsFinder/drive/view/" + avatar;
        }

        if (avatar.contains("id=")) {
            String id = avatar.substring(avatar.indexOf("id=") + 3);
            int idx = id.indexOf("&");
            if (idx != -1)
                id = id.substring(0, idx);

            return "http://localhost:8080/tutorsFinder/drive/view/" + id;
        }

        return avatar;
    }
}
