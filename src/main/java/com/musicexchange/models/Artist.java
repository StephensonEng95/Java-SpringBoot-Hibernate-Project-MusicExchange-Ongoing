package com.musicexchange.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "artists")
@Data
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artistid") // Primary key name from original DB
    private Long artistId;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "password", nullable = false, length = 255)
    @ToString.Exclude
    private String password;
    @Column(name = "is_active")
    private boolean isActive;
    @Column(name = "release_date")
    private LocalDate releaseDate;
    @Column(name = "release_time")
    private LocalTime releaseTime;

    // Establishing relationship so we can pull songs for the dashboard
    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Song> songs;

    @PrePersist
    public void onCreate(){
        this.isActive = true;
        this.releaseDate = LocalDate.now();
        this.releaseTime = LocalTime.now();
    }
}