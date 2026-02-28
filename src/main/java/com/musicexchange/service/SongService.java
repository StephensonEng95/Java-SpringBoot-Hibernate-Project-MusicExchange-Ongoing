package com.musicexchange.service;

import com.musicexchange.models.Artist;
import com.musicexchange.models.Song;
import com.musicexchange.repository.ArtistRepository;
import com.musicexchange.repository.SongRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SongService {

    private final SongRepository songRepo;
    private final ArtistRepository artistRepo;

    // Using constructor injection as the preferred way to manage dependencies
    public SongService(SongRepository songRepo, ArtistRepository artistRepo) {
        this.songRepo = songRepo;
        this.artistRepo = artistRepo;
    }

    // Standard method to save a song record
    public void addSong(Song song) {
        songRepo.save(song);
    }

    // Assigns an artist to a song before persisting it to the database
    public Song addSongToArtist(Long artistid, Song song) {
        Artist artist = artistRepo.findById(artistid)
                .orElseThrow(() -> new RuntimeException("Artist not found for ID: " + artistid));

        song.setArtist(artist);
        return songRepo.save(song);
    }

    // Returns the full list of songs from the database
    public List<Song> getAllSongs() {
        return (List<Song>) songRepo.findAll();
    }

    // Updated parameter to song_id to match the primary key rename
    public Song getSongById(Long song_id) {
        Optional<Song> optionalSong = songRepo.findById(song_id);
        if (optionalSong.isPresent()) {
            return optionalSong.get();
        } else {
            return null;
        }
    }

    // Persists changes to an existing song
    public void updateSong(Song song) {
        songRepo.save(song);
    }

    // Deletes a song record based on the updated song_id field
    public void deleteSong(Long song_id) {
        if (!songRepo.existsById(song_id)) {
            throw new RuntimeException("Delete failed: Song ID " + song_id + " does not exist.");
        }
        songRepo.deleteById(song_id);
    }

    // Fetches all songs uploaded by a specific artist
    public List<Song> getSongsByArtist(Long artistid) {
        // Fetching the Artist object first to avoid the 'Artistid' naming conflict in JPA
        Artist artist = artistRepo.findById(artistid)
                .orElseThrow(() -> new RuntimeException("Artist not found for ID: " + artistid));

        return songRepo.findByArtist(artist);
    }
}