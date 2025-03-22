package com.musicexchange.musicexchange.models;


import jakarta.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name="artists")
@Inheritance(strategy=InheritanceType.JOINED)
public class Artist {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="artistID",nullable=false)
	protected Long id;
	
	@Column(name="username",nullable=false,unique=true)
	private String username;
	@Column(name="email",nullable=false,unique=true)
	private String email;
	@Column(name="password",nullable=false, unique=true)
	private String password;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
