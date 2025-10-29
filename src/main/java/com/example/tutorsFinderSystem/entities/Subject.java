package com.example.tutorsFinderSystem.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "subjects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Long subjectId;

    @Column(name = "subject_name", nullable = false, unique = true, length = 100)
    private String subjectName;

    // Một môn học có thể được nhiều gia sư dạy
    @ManyToMany(mappedBy = "subjects")
    private Set<Tutor> tutors = new HashSet<>();
}
