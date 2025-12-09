package com.example.tutorsFinderSystem.repositories;

import com.example.tutorsFinderSystem.entities.Tutor;
import com.example.tutorsFinderSystem.enums.Role;
import com.example.tutorsFinderSystem.enums.UserStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TutorRepository extends JpaRepository<Tutor, Long> {
    Optional<Tutor> findByUserUserId(Long userId);

    Optional<Tutor> findByUser_Email(String email);

    @Query("""
            SELECT t
            FROM Tutor t
            JOIN t.user u
            WHERE :role MEMBER OF u.roles
            """)
    List<Tutor> findAllTutors(@Param("role") Role role);

    @Query("""
                SELECT t
                FROM Tutor t
                JOIN t.user u
                WHERE :role MEMBER OF u.roles
            """)
    Page<Tutor> findAllTutorsPageable(@Param("role") Role role,Pageable pageable);

    // Lấy certificates theo tutor_id (từ bảng tutor_certificates)
    @Query(value = """
            SELECT certificate
            FROM tutor_certificates
            WHERE tutor_id = :tutorId
            """, nativeQuery = true)
    List<String> findCertificatesByTutorId(@Param("tutorId") Long tutorId);

    // (Optional) Lọc theo status user
    @Query("""
            SELECT t
            FROM Tutor t
            JOIN t.user u
            WHERE :role MEMBER OF u.roles
              AND u.status = :status
            """)
    List<Tutor> findByUserStatus(@Param("role") Role role,@Param("status") UserStatus status);

    // Lấy tất cả tutor có verification_status = PENDING, sắp xếp theo ngày nộp từ cũ đến mới
    // List<Tutor> findByVerificationStatusOrderByUserCreatedAtAsc(TutorStatusverificationStatus);
    @Query("""
                SELECT t
                FROM Tutor t
                JOIN t.user u
                WHERE t.verificationStatus = com.example.tutorsFinderSystem.enums.TutorStatus.PENDING
            """)
    Page<Tutor> findPendingTutors(Pageable pageable);

    @Query(value = """
                SELECT COUNT(*)
                FROM tutors t
                WHERE t.verification_status = 'PENDING'
            """, nativeQuery = true)
    long countPendingTutors();


}
