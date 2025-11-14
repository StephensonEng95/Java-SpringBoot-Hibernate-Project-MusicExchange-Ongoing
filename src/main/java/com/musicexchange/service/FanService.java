package com.musicexchange.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.musicexchange.models.Fan;
import com.musicexchange.repository.FanRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
 * Fan service layer handling business logic for 
 * fan entities
 */

@Service
@Transactional
@Slf4j
public class FanService {
	private FanRepository fanRepo;
	private PasswordEncoder passwordEncoder;
	
	public FanService(FanRepository fanrepo, PasswordEncoder passwordEncoder) {
		
		this.fanRepo = fanrepo;
		this.passwordEncoder = passwordEncoder;
	}
	
	/**
	 * creates new fan with provided parameters at signup
	 * @param username fan's username
	 * @param email fan's email
	 * @param rawPassword plain text password to be encoded
	 * @return savedFan fan's entity 
	 */
	public Fan createFan(String username,String email,String rawPassword)
	{
		log.info("Creating fan with username: {}", username);
		if(fanRepo.existsByUsername(username)) {
			log.warn("duplicate username creation attempt: {}" + username);
			throw new RuntimeException("username '" + username + "' is already taken" );
		}
		
		if(fanRepo.existsByEmail(email)) {
			log.warn("duplicate email creation attempt: {}" + email);
			throw new RuntimeException("email'" + email + "'is already taken");
		}
		
		Fan fan = new Fan();
		fan.setUsername(username); 
		fan.setEmail(email); 
		fan.setPassword(passwordEncoder.encode(rawPassword)); 

		Fan savedFan = fanRepo.save(fan);
		log.debug("Fan created succesfully with id: {}", savedFan.getId());
		return savedFan;
	} 
	
	public Optional<Fan> authenticateFan(String username, String rawPassword)
	 {
		 log.info("Authenticating artist with username: {} and password: {}" + username + rawPassword);
		 Optional<Fan> fan = fanRepo.findByUsername(username);
	     if (fan.isPresent() && passwordEncoder.matches(rawPassword, fan.get().getPassword())) {
	    	 log.debug("Authentication successful");
	    	 return fan;
	        }
	     log.error("Authentication failed");
	        return Optional.empty();
	    }
	public void updateFanEmail(Long id,String email)
	{
		log.info("updating artist email with id: {}" + email + id);
		Optional<Fan> artistOpt = fanRepo.findById(id);
		if(artistOpt.isPresent()) {
			fanRepo.updateFanEmail(id, email);
			log.debug("Fan with id: {}" + id + "updated succesfully");
		}
		else {
			log.warn("Attempted to update non existent fan with id: {}" + id);
			throw new RuntimeException("Can't find Fan with id" + id);
		}		
	} 
	 
    public void updateFanPassword(Long id, String password) 
    {
		fanRepo.updateFanPassword(id, password);
	}
	
	
	public void deleteFan(Long id) 
	{
		fanRepo.deleteFan(id);
	}
	
}
