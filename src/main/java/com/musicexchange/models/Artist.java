package com.musicexchange.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
    @Column(name = "profile_picture")
    private String profilePicture;

    // Establishing relationship so we can pull songs for the dashboard
    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Song> songs = new ArrayList<>();

    @PrePersist
    public void onCreate(){
        this.isActive = true;
        this.profilePicture = "default-profile.png";
        this.releaseDate = LocalDate.now();
        this.releaseTime = LocalTime.now();
    }
}