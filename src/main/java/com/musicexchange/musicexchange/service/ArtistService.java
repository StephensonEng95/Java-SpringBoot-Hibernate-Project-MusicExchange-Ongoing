package com.musicexchange.musicexchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import org.springframework.beans.factory.annotation.Autowired;

import com.musicexchange.musicexchange.models.Artist;
import com.musicexchange.musicexchange.repository.ArtistRepository;

import jakarta.transaction.Transactional;

@Service
public class ArtistService {
	
	private final ArtistRepository userrepo;
	
	Artist art=new Artist();
	@Autowired
	public ArtistService(ArtistRepository userrepo) {
        this.userrepo = userrepo;
    }
	@Transactional
	public Artist createArtist(String username,String email,String password){
		Artist at=new Artist();
		at.setUsername(username);
		at.setPassword(password);
		at.setEmail(email);
		return userrepo.save(at);
	}
	
	@Transactional
	void updateArtistEmail(Long id,String email) {
		art.setEmail(email);
		userrepo.save(art);
		
	}
	
	void updatePassword(Long id, String password) {
		art.setPassword(password);
		userrepo.save(art);
	}
	
	
	
}
