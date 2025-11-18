package com.example.tutorsFinderSystem.repositories;

import com.example.tutorsFinderSystem.entities.Ratings;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Ratings, Long> {

    @Query("""
        SELECT AVG(r.score)
        FROM Ratings r
        JOIN ClassEntity c ON r.classEntity.classId = c.classId
        JOIN ClassRequest cr ON c.classRequest.requestId = cr.requestId
        WHERE cr.tutor.tutorId = :tutorId
    """)
    Double getAverageRating(Long tutorId);
}
