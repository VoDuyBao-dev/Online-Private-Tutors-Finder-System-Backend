package com.example.tutorsFinderSystem.repositories;

import com.example.tutorsFinderSystem.dto.response.FeaturedTutorResponse;
import com.example.tutorsFinderSystem.entities.Tutor;
import com.example.tutorsFinderSystem.enums.TutorStatus;
import com.example.tutorsFinderSystem.enums.UserStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TutorRepository extends JpaRepository<Tutor, Long> {

    Optional<Tutor> findByUserUserId(Long userId);

    Optional<Tutor> findByUser_Email(String email);

    List<Tutor> findByVerificationStatus(TutorStatus status);

    // ===================== ROLE =====================

    @Query("""
            SELECT t
            FROM Tutor t
            JOIN t.user u
            WHERE :role MEMBER OF u.roles
            """)
    List<Tutor> findAllTutors(@Param("role") String role);

    @Query("""
            SELECT t
            FROM Tutor t
            JOIN t.user u
            WHERE :role MEMBER OF u.roles
            """)
    Page<Tutor> findAllTutorsPageable(
            @Param("role") String role,
            Pageable pageable);

    // ===================== ROLE + STATUS =====================

    @Query("""
            SELECT t
            FROM Tutor t
            JOIN t.user u
            WHERE :role MEMBER OF u.roles
              AND u.status = :status
            """)
    List<Tutor> findByUserStatus(
            @Param("role") String role,
            @Param("status") UserStatus status);

    // ===================== PENDING TUTORS =====================

    @Query("""
            SELECT t
            FROM Tutor t
            WHERE t.verificationStatus = com.example.tutorsFinderSystem.enums.TutorStatus.PENDING
            """)
    Page<Tutor> findPendingTutors(Pageable pageable);

    @Query(value = """
            SELECT COUNT(*)
            FROM tutors t
            WHERE t.verification_status = 'PENDING'
            """, nativeQuery = true)
    long countPendingTutors();

    List<Tutor> findAll(Specification<Tutor> spec);

    @Query(value = """
            SELECT
                t.tutor_id,
                u.full_name,
                u.avatar_image,
                MIN(s.subject_name) AS subject_name,
                t.address,
                t.price_per_hour,
                AVG(r.score) AS avg_rating,
                COUNT(r.rating_id) AS total_ratings
            FROM tutors t
            JOIN users u ON u.user_id = t.user_id
            LEFT JOIN tutor_subjects ts ON ts.tutor_id = t.tutor_id
            LEFT JOIN subjects s ON s.subject_id = ts.subject_id
            LEFT JOIN class_requests cr ON cr.tutor_id = t.tutor_id
            LEFT JOIN classes c ON c.request_id = cr.request_id
            LEFT JOIN ratings r ON r.class_id = c.class_id
            WHERE t.verification_status = 'APPROVED'
              AND u.status = 'ACTIVE'
            GROUP BY t.tutor_id, u.full_name, u.avatar_image, t.address, t.price_per_hour
            HAVING COUNT(r.rating_id) > 0
            ORDER BY avg_rating DESC, total_ratings DESC
            LIMIT 4
            """, nativeQuery = true)
    List<Object[]> findFeaturedTutorsRaw();

}
