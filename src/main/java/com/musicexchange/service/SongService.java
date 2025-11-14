package com.musicexchange.service;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.musicexchange.models.Artist;
import com.musicexchange.models.Song;
import com.musicexchange.repository.ArtistRepository;
import com.musicexchange.repository.SongRepository;

import jakarta.transaction.Transactional;
/**
 * Song service layer handling business logic
 * for songs entities
 */
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class SongService { 
	
	private SongRepository songRepository;
	private ArtistRepository artistRepository;
	
	public SongService(SongRepository songsRepository, ArtistRepository artistRepository) {
		this.songRepository = songsRepository;
		this.artistRepository = artistRepository;
	}
	
	public Song addSong(String songTitle, String genre, int duration, LocalDate release_date, String artistUsername) 
	{   
	    log.info("Adding song for artist: {}", artistUsername);
	    
	    if(songRepository.existsBySongTitle(songTitle)) {
	    	log.warn("Song already exists" + songTitle);
	    	throw new RuntimeException("Song already created" + songTitle);
	    }
	    
	    Artist artist = artistRepository.findByUsername(artistUsername)
	            .orElseThrow(() -> new RuntimeException("Artist not found with username: " + artistUsername));
	    
	    Song song = new Song();
	    song.setSongTitle(songTitle);  
	    song.setGenre(genre);
	    song.setDuration(duration); 
	    song.setReleaseDate(release_date);
	    song.setArtist(artist);
	    artist.addSong(song);
	    log.debug("Song added successfully for artist: {}", artistUsername);
	    return songRepository.save(song); 
	}
	
	public List<Song> getSongsByArtistId(Long artistId) {
	    return songRepository.findByArtistId(artistId);
	} 
	
	public List<Song> getAllSongs(){
		Artist artist = new Artist();
		return artist.getSongs();
	}
	@Transactional
	public void deleteSong(String songTitle) 
	{
		songRepository.deleteSongByTitle(songTitle); 
	}  
	

}
