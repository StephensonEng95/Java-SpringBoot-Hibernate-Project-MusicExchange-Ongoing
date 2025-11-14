package com.musicexchange.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import java.util.List;

import jakarta.persistence.Column;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * Artist class containing artist's properties and
 * helper methods for OneToMany relationships
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@Slf4j
@Table(name = "artists")  
public class Artist {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "artist_id",nullable=false)
	@ToString.Include
	private Long id; 
	 
	@Column(name = "username",nullable = false,unique = true)
	@ToString.Include
	private String username;
	
	@Column(name = "email",nullable = false,unique = true)
	private String email; 
	@Column(name = "password",nullable = false, length = 8) 
	private String password;
	
	@Column(name = "date_created", nullable = false)
	private LocalDate dateCreated;
	@Column(name = "time_created", nullable = false)
	private LocalTime timeCreated;
	
	@Column(name = "is_active", nullable = false)
	private boolean isActive = true;
	
	@PrePersist 
	public void onCreateTime() 
	{
		dateCreated = LocalDate.now();
		timeCreated = LocalTime.now();
	}
	
	@OneToMany(mappedBy ="artist")
	private List<Song> songs = new ArrayList<>();
	
	public void addSong(Song song)
	{
		log.info("adding song to artist's song list");
		songs.add(song);
		song.setArtist(this);
		log.debug("Song added successfully");
	} 
	
	@OneToMany(mappedBy = "followedArtist")
	private List<Fan> fans = new ArrayList<>();
	
	public void addFan(Fan fan) 
	{
		log.info("Adding fan to artist's fan list");
		fans.add(fan);
		fan.setFollowedArtist(this); 
	}
}  
