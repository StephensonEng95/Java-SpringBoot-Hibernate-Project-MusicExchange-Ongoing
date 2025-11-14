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
 * Fan class containing song properties
 * and ManyToOne relationship to indicate multiple fans
 * can belong to one song
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@Table(name ="fans")
public class Fan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fan_id",nullable=false)
	@ToString.Include
	private Long id;
	
	@Column(name = "username", nullable = false)
	@ToString.Include
	private String username;
	
	@Column(name = "email", nullable = false, unique =true)
	private String email;
	
	@Column(name = "password", nullable = false, length = 8)
	private String password;
	
	@Column(name = "date_created", nullable = false)
	private LocalDate dateCreated;
	
	@Column(name ="time_created", nullable = false)
	private LocalTime timeCreated;
	
	@Column(name = "is_active", nullable = false)
	private boolean isActive = true; 
	
	@PrePersist
	public void onCreateTime() 
	{
		dateCreated = LocalDate.now();
		timeCreated = LocalTime.now();
		
	}
	
	@ManyToOne
	@JoinColumn(name= "artist_id") 
	private Artist followedArtist;
}
