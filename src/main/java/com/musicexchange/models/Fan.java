package com.musicexchange.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "fans")
@Data
public class Fan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fan_id") // Matches the primary key in the DB
    private Long fanId;

    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "password", nullable = false, length = 255)
    @ToString.Exclude
    private String password;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "created_At")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "profile_picture")
    private String profilePicture;


    @ManyToMany
    @JoinTable(
            name ="artists_fans",
            joinColumns = @JoinColumn(name = "fan_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id")
    )
    private List<Artist> artist;

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