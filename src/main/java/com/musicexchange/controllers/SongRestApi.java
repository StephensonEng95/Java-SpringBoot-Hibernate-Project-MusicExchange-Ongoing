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

    //constructor injectionn followinf dependency inversion of SOLID principles
    public SongRestApi(SongService songService) {
        this.songService = songService;
    }

    // Returns all songs in the database as a JSON list
    @GetMapping
    public List<Song> getAllSongs() {
        return songService.getAllSongs();
    }

    // Fetches songs for a specific artist using the artistId
    @GetMapping("/artist/{artistId}")
    public ResponseEntity<List<Song>> getSongsByArtist(@PathVariable Long artistId) {
        List<Song> songs = songService.getSongsByArtist(artistId);
        return ResponseEntity.ok(songs);
    }

    // Endpoint to delete a song based on the unique song_id primary key
    @DeleteMapping("/{song_id}")
    public ResponseEntity<String> deleteSong(@PathVariable Long songId) {
        try {

            songService.deleteSong(songId);
            return ResponseEntity.ok("Song deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }
}