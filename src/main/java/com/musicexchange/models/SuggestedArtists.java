package com.musicexchange.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "suggested_artists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuggestedArtists {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    private String profilePic;

    @Column(nullable = false)
    private String genre;
}