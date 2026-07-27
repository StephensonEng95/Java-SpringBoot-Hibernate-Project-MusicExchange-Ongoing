package com.musicexchange.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "artists")
@Data
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artist_id") // Primary key name from original DB
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
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "profile_picture")
    private String profilePicture;

    // Establishing relationship so we can pull songs for the dashboard
    @OneToMany(mappedBy = "artist")
    private List<Song> songs;

    @ManyToMany(mappedBy = "artist")
    private List<Fan> fans;
    @PrePersist()
    public void onCreate(){
        this.isActive = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt= LocalDateTime.now();
    }

    @PreUpdate()
    public void onUpdate(){
        this.updatedAt = LocalDateTime.now();

    }

}