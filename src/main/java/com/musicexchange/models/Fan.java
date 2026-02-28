package com.musicexchange.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

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

    @Column(name = "is_Active")
    private boolean isActive;
    @Column(name = "release_date")
    private LocalDate releaseDate;
    @Column(name = "release_time")
    private LocalTime releaseTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artistid")
    private Artist artist;

    @PrePersist()
    public void onCreate(){
        this.isActive = true;
        this.releaseDate = LocalDate.now();
        this.releaseTime = LocalTime.now();
    }
}