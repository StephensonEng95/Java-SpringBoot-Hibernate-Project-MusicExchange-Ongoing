package com.musicexchange.musicexchange.service;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;

import com.musicexchange.musicexchange.models.Songs;
import com.musicexchange.musicexchange.repository.*;

import jakarta.transaction.Transactional;
public class SongsService {
	@Autowired
	private SongsRepository songsRepo;
	
	@Transactional
	public Songs createSong(String name,LocalDate releaseDate) {
		Songs s=new Songs();
		s.setSongName(name);
		s.setReleaseDate(releaseDate);
		return songsRepo.save(s);
	}
	
	@Transactional
	public void deleteSong(Long songId) {
		songsRepo.deleteSong(songId);
	}
	

}
