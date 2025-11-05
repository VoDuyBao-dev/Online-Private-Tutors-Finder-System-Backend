package com.example.tutorsFinderSystem.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "stickers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sticker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sticker_id")
    private Long stickerId;

    @Column(name = "image_url", nullable = false, length = 500)
    private String imageUrl;
}
