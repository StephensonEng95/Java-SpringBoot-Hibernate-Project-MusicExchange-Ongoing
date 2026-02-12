package com.musicexchange.controllers;

import com.musicexchange.models.Song;
import com.musicexchange.service.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/songs")
public class SongRestApi {

    private final SongService songService;

    public SongRestApi(SongService songService) {
        this.songService = songService;
    }

    // Get the full list of songs as JSON
    @GetMapping
    public List<Song> getAllSongs() {
        return songService.getAllSongs();
    }

    // Get songs for a specific artist via their ID
    @GetMapping("/artist/{artistId}")
    public ResponseEntity<List<Song>> getSongsByArtist(@PathVariable Long artistId) {
        List<Song> songs = songService.getSongsByArtistId(artistId);
        return ResponseEntity.ok(songs);
    }

    // API endpoint to delete a song by title
    @DeleteMapping("/{title}")
    public ResponseEntity<String> deleteSong(@PathVariable String title) {
        try {
            songService.deleteSong(title);
            return ResponseEntity.ok("Song deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}