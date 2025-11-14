package com.musicexchange.service;

import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.musicexchange.models.Artist;
import com.musicexchange.repository.ArtistRepository;
 
import jakarta.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;


/**
 * Artist service layer handling business logic for 
 * artist entities
 */
@Service
@Transactional
@Slf4j
public class ArtistService {
	
	private ArtistRepository artistRepo; 
	private PasswordEncoder passwordEncoder;
	
	public ArtistService(ArtistRepository artistRepo, PasswordEncoder passwordEncoder)
	{
        this.artistRepo = artistRepo;
        this.passwordEncoder = passwordEncoder;
    }
	/**
	 * creates new artist with provided parameters at signup
	 * @param username artist's username
	 * @param email artist's email
	 * @param rawPassword plain text password to be encoded
	 * @return savedArtist artist's entity 
	 */
	public Artist createArtist(String username,String email,String rawPassword)
	{
		log.info("Creating artist with username: {}", username);
		if(artistRepo.existsByUsername(username)) {
			log.warn("duplicate username creation attempt: {}" + username);
			throw new RuntimeException("username '" + username + "' is already taken" );
		}
		
		if(artistRepo.existsByEmail(email)) {
			log.warn("duplicate email creation attempt: {}" + email);
			throw new RuntimeException("email '" + email + "' is already taken");
		}
		
		Artist artist=new Artist();
		artist.setUsername(username); 
		artist.setEmail(email); 
		artist.setPassword(passwordEncoder.encode(rawPassword)); 

		Artist savedArtist = artistRepo.save(artist);
		log.debug("Artist created succesfully with id: {}", savedArtist.getId());
		return savedArtist;
	} 
	 public Optional<Artist> authenticateArtist(String username, String rawPassword)
	 {
		 log.info("Authenticating artist with username: {} and password: {}" + username + rawPassword);
		 Optional<Artist> artist = artistRepo.findByUsername(username);
	     if (artist.isPresent() && passwordEncoder.matches(rawPassword, artist.get().getPassword())) {
	    	 log.debug("Authentication successful");
	    	 return artist;
	        }
	     log.error("Authentication failed");
	        return Optional.empty();
	    }
	 
	public void updateArtistEmail(Long id,String email)
	{
		log.info("updating artist email with id: {}" + email + id);
		Optional<Artist> artistOpt = artistRepo.findById(id);
		if(artistOpt.isPresent()) {
			artistRepo.updateArtistEmail(id, email);
			log.debug("Artist with id: {}" + id + "updated succesfully");

		}
		else { 
			log.warn("Attempted to update non existent Artist with id: {}" + id);

			throw new RuntimeException("Can't find Artist with id" + id);
		}		
		
	} 
	
	
    public void updateArtistPassword(Long id, String password) 
    {
		artistRepo.updateArtistPassword(id, password);
	}
	
	
	public void deleteArtist(Long id) 
	{
		artistRepo.deleteArtist(id);
	}
	
	
	
}
