package com.musicexchange.models;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 * Song class containing song properties
 * and ManyToOne relationship to indicate multiple songs
 * can belong to one song
 */ 
@Entity
@Getter
@Setter 
@NoArgsConstructor
@AllArgsConstructor
@ToString()
@Table(name = "songs")
public class Song{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "song_id", nullable = false) 
	private Long songId;
	@Column(name = "song_title",nullable = false) 
	private String songTitle;
	@Column(name = "genre",nullable = false)
    private String genre; 
	@Column(name = "duration",nullable = false)
    private int duration;
	@Column(name = "release_date",nullable = false,unique = true)
	private LocalDate releaseDate;
	
	@Column(name = "date_created", nullable = false)
	private LocalDate dateCreated;
	
	@Column(name = "time_created", nullable = false)
	private LocalTime timeCreated;
	
	@PrePersist 
	public void onCreateTime() 
	{
		dateCreated = LocalDate.now();
		timeCreated = LocalTime.now();
	}
	@ManyToOne
	@JoinColumn(name="artist_id")
	private Artist artist;  
	 
	

}
