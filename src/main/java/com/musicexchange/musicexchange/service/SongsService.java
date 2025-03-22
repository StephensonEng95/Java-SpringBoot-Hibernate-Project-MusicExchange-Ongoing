package com.musicexchange.musicexchange.service;
import org.springframework.beans.factory.annotation.Autowired;

import com.musicexchange.musicexchange.models.Songs;
import com.musicexchange.musicexchange.repository.*;
public class SongsService {
	@Autowired
	SongsRepository songsrepo;
	
	public void createSong(Songs song) {
		songsrepo.save(song);
		
	}

}
