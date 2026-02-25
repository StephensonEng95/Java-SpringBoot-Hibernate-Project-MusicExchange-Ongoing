package com.musicexchange.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "songs")
@Data
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "song_id")
    private Long songId;

    // Field names match the database columns for consistency
    private String songTitle;
    private String genre;
    private int duration;

    @Column(name = "release_date")
    private LocalDate releaseDate;
    @Column(name = "release_time")
    private LocalTime releaseTime;


    // Mapping the relationship back to the Artist table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artistid")
    private Artist artist;

    @PrePersist()
    public void onCreate(){

        this.releaseDate = LocalDate.now();
        this.releaseTime = LocalTime.now();
    }

}