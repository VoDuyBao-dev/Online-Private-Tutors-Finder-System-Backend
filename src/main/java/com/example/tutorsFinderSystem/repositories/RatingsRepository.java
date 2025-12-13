package com.example.tutorsFinderSystem.repositories;


import com.example.tutorsFinderSystem.entities.Ratings;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingsRepository extends JpaRepository<Ratings, Long> {

    // Lấy tất cả đánh giá của 1 tutor
    @Query("""
        SELECT r FROM Ratings r
        JOIN r.classEntity c
        JOIN c.classRequest cr
        WHERE cr.tutor.tutorId = :tutorId
        ORDER BY r.createdAt DESC
    """)
    List<Ratings> findAllByTutorId(@Param("tutorId") Long tutorId);

    // Lấy N đánh giá gần nhất
    @Query("""
        SELECT r FROM Ratings r
        JOIN r.classEntity c
        JOIN c.classRequest cr
        WHERE cr.tutor.tutorId = :tutorId
        ORDER BY r.createdAt DESC
    """)
    List<Ratings> findRecentByTutorId(@Param("tutorId") Long tutorId, Pageable pageable);

    // Tính điểm trung bình
    @Query("""
        SELECT AVG(r.score) FROM Ratings r
        JOIN r.classEntity c
        JOIN c.classRequest cr
        WHERE cr.tutor.tutorId = :tutorId
    """)
    Double calculateAverageScore(@Param("tutorId") Long tutorId);

    // Đếm tổng số đánh giá
    @Query("""
        SELECT COUNT(r) FROM Ratings r
        JOIN r.classEntity c
        JOIN c.classRequest cr
        WHERE cr.tutor.tutorId = :tutorId
    """)
    Long countByTutorId(@Param("tutorId") Long tutorId);

    // Đếm số lượng theo từng mức sao
    @Query("""
        SELECT COUNT(r) FROM Ratings r
        JOIN r.classEntity c
        JOIN c.classRequest cr
        WHERE cr.tutor.tutorId = :tutorId AND r.score >= :minScore AND r.score < :maxScore
    """)
    Long countByTutorIdAndScoreRange(
            @Param("tutorId") Long tutorId,
            @Param("minScore") Float minScore,
            @Param("maxScore") Float maxScore
    );

    boolean existsByClassEntity_ClassId(Long classId);
}
