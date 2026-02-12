package com.musicexchange.service;

import com.musicexchange.models.Artist;
import com.musicexchange.models.Song;
import com.musicexchange.repository.ArtistRepository;
import com.musicexchange.repository.SongRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@Slf4j
public class SongService {

    private final SongRepository songRepo;
    private final ArtistRepository artistRepo;

    public SongService(SongRepository songRepo, ArtistRepository artistRepo) {
        this.songRepo = songRepo;
        this.artistRepo = artistRepo;
    }

    // Saves a new song and links it to an artist
    public Song createSong(String title, String genre, int duration, LocalDate releaseDate, String username) {
        log.info("Adding new track for: {}", username);

        if(songRepo.existsBySongTitle(title)) {
            throw new RuntimeException("Song title already exists: " + title);
        }

        Artist artist = artistRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Artist not found: " + username));

        Song song = new Song();
        song.setSongTitle(title);
        song.setGenre(genre);
        song.setDuration(duration);
        song.setReleaseDate(releaseDate);
        song.setArtist(artist);

        // Link the song to the artist's list
        artist.addSong(song);

        log.debug("Track saved successfully");
        return songRepo.save(song);
    }

    // Fetch songs for a specific artist
    public List<Song> getSongsByArtistId(Long artistId) {
        return songRepo.findByArtistId(artistId);
    }

    // Returns every song in the database
    public List<Song> getAllSongs() {
        return songRepo.findAll();
    }

    // Remove a song by its title
    public void deleteSong(String title) {
        if(!songRepo.existsBySongTitle(title)) {
            throw new RuntimeException("Cannot delete: Song not found");
        }
        songRepo.deleteBySongTitle(title);
        log.info("Song deleted: {}", title);
    }
}