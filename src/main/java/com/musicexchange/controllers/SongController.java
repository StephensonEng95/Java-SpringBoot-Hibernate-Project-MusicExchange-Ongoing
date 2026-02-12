package com.musicexchange.controllers;

import com.musicexchange.service.SongService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/song")
public class SongController {

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    // Directs to the song upload page
    @GetMapping("/add")
    public String showAddSongForm() {
        return "add-song";
    }

    // Handles saving the song data from the form
    @PostMapping("/add")
    public String createSong(@RequestParam String songTitle,
                             @RequestParam String genre,
                             @RequestParam int duration,
                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate releaseDate,
                             @RequestParam String username,
                             Model model) {
        try {
            // Service handles the logic and linking to the artist
            songService.createSong(songTitle, genre, duration, releaseDate, username);

            // Redirecting to the list view after success
            return "redirect:/song/all";

        } catch (Exception e) {
            model.addAttribute("error", "Failed to add song: " + e.getMessage());
            return "add-song";
        }
    }

    // List all songs in the app
    @GetMapping("/all")
    public String getAllSongs(Model model) {
        model.addAttribute("songs", songService.getAllSongs());
        return "songs-list";
    }

    // View songs for one specific artist
    @GetMapping("/artist/{artistId}")
    public String getSongsByArtist(@PathVariable Long artistId, Model model) {
        model.addAttribute("songs", songService.getSongsByArtistId(artistId));
        return "songs-list";
    }
}