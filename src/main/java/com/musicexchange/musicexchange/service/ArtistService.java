package com.musicexchange.musicexchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import org.springframework.beans.factory.annotation.Autowired;

import com.musicexchange.musicexchange.models.Artist;
import com.musicexchange.musicexchange.repository.ArtistRepository;

import jakarta.transaction.Transactional;

@Service
public class ArtistService {
	@Autowired
	private ArtistRepository artistRepo;
	
	/*@Autowired
	public ArtistService(ArtistRepository userrepo) {
        this.userrepo = userrepo;
    }*/
	@Transactional
	public Artist createArtist(String username,String email,String password){
		Artist at=new Artist();
		at.setUsername(username);
		at.setPassword(password);
		at.setEmail(email);
		return artistRepo.save(at);
	}
	
	@Transactional
	public void updateArtistEmail(Long id,String email) {
		artistRepo.updateArtistEmail(id, email);
		
	}
	
	@Transactional
    public void updateArtistPassword(Long id, String password) {
		artistRepo.updateArtistPassword(id, password);
	}
	
	@Transactional
	public void deleteArtist(Long id) {
		artistRepo.deleteArtist(id);
	}
	
	
	
}
