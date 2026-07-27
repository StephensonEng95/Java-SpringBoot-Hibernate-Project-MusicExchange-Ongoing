package com.musicexchange.controllers;

import com.musicexchange.dto.SongRequestDto;
import com.musicexchange.dto.SongResponseDto;
import com.musicexchange.service.SongService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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

    @PostMapping
    public ResponseEntity<SongResponseDto> addSong(@Valid @RequestBody SongRequestDto request) {
        SongResponseDto createdSong = songService.addSong(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSong);
    }

    @GetMapping
    public ResponseEntity<List<SongResponseDto>> getAllSongs() {
        List<SongResponseDto> songs = songService.getAllSongs();
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/{songId}")
    public ResponseEntity<SongResponseDto> getSongById(@PathVariable Long songId) {
        SongResponseDto song = songService.getSongById(songId);
        return ResponseEntity.ok(song);
    }

    @GetMapping("/artist/{artistId}")
    public ResponseEntity<List<SongResponseDto>> getSongsByArtist(@PathVariable Long artistId) {
        List<SongResponseDto> songs = songService.getSongsByArtist(artistId);
        return ResponseEntity.ok(songs);
    }

    @PutMapping("/{songId}")
    public ResponseEntity<SongResponseDto> updateSong(@PathVariable Long songId,
                                                      @Valid @RequestBody SongRequestDto request) {
        SongResponseDto updatedSong = songService.updateSongByIdAndTitle(songId, request);
        return ResponseEntity.ok(updatedSong);
    }

    @DeleteMapping("/{songId}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long songId) {
        songService.deleteSong(songId);
        return ResponseEntity.noContent().build();
    }
}