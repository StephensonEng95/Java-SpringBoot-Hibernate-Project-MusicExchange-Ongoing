package com.musicexchange.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "songs")
@Data
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "song_id")
    private Long songId;

    private String songTitle;
    private String genre;
    private Long duration;
    private String coverArt;
    @Column(name = "released_At")
    private LocalDateTime releasedAt;


    // Mapping the relationship back to the Artist table
    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @PrePersist()
    public void onCreate(){

        this.releasedAt = LocalDateTime.now();

    }

}