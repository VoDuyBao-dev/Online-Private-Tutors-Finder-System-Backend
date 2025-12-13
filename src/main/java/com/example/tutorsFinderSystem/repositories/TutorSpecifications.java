package com.example.tutorsFinderSystem.repositories;

import com.example.tutorsFinderSystem.entities.Subject;
import com.example.tutorsFinderSystem.entities.Tutor;
import com.example.tutorsFinderSystem.entities.User;
import com.example.tutorsFinderSystem.enums.Gender;
import com.example.tutorsFinderSystem.enums.TutorStatus;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Join;

public class TutorSpecifications {

    public static Specification<Tutor> approvedOnly() {
        return (root, query, cb) -> cb.equal(root.get("verificationStatus"), TutorStatus.APPROVED);
    }

    public static Specification<Tutor> hasGender(Gender gender) {
        return (root, query, cb) -> cb.equal(root.get("gender"), gender);
    }

    // public static Specification<Tutor> hasEducationalLevel(String level) {
    // return (root, query, cb) -> cb.equal(root.get("educationalLevel"), level);
    // }
    public static Specification<Tutor> hasEducationalLevel(String educationalLevel) {
        return (root, query, cb) -> cb.equal(
                cb.lower(root.get("educationalLevel")), 
                educationalLevel.trim().toLowerCase());
    }

    public static Specification<Tutor> hasTutorId(Long tutorId) {
        return (root, query, cb) -> cb.equal(root.get("tutorId"), tutorId);
    }

    public static Specification<Tutor> teachesSubject(Long subjectId) {
        return (root, query, cb) -> {
            query.distinct(true);
            var subjects = root.join("subjects", JoinType.INNER);
            return cb.equal(subjects.get("subjectId"), subjectId);
        };
    }

    public static Specification<Tutor> keywordSearch(String keyword) {

        return (root, query, cb) -> {
            query.distinct(true);

            String kw = "%" + keyword.trim().toLowerCase() + "%";

            // tutors -> users
            Join<Tutor, User> userJoin = root.join("user", JoinType.INNER);

            // tutors -> subjects (tutor_subjects)
            Join<Tutor, Subject> subjectJoin = root.join("subjects", JoinType.LEFT);

            return cb.or(
                    cb.like(cb.lower(userJoin.get("fullName")), kw), // tên tutor
                    cb.like(cb.lower(root.get("educationalLevel")), kw), // trình độ
                    cb.like(cb.lower(root.get("university")), kw), // trường
                    cb.like(cb.lower(root.get("introduction")), kw), // giới thiệu
                    cb.like(cb.lower(subjectJoin.get("subjectName")), kw) // môn học
            );
        };
    }

}
