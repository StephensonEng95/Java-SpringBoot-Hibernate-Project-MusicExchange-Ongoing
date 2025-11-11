package com.musicexchange.musicexchange.models;

import java.time.LocalDate;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;

@Entity
@Table(name="songs")
public class Songs extends Artist{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(nullable=false)
	private Long songId;
	@Column(name="songNames",nullable=false,unique=true)
	private String songName;
	@Column(name="releaseDate",nullable=false,unique=true)
	private LocalDate releaseDate;
	
	/*@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="fk_artistId")
	private User user;*/
	
	public String getSongName() {
		return songName;
	}
	public void setSongName(String songName) {
		this.songName = songName;
	}
	public LocalDate getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}
	/*public Long getSongId() {
		return songId;
	}
	public void setSongId(Long songId) {
		this.songId = songId;
	}*/
	

}
