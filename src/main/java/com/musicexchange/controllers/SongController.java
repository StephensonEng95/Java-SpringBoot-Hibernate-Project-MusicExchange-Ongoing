package com.musicexchange.controllers;

import com.musicexchange.models.Song;
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

    @GetMapping("/add")
    public String showAddSongForm() {
        return "add-song";
    }

    // Processing the song upload and connecting it to the artist ID
    @PostMapping("/add")
    public String createSong(@RequestParam String songTitle,
                             @RequestParam String genre,
                             @RequestParam int duration,
                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate release_date,
                             @RequestParam Long artistId,
                             Model model) {
        try {
            Song song = new Song();

            // Setting values using the names generated from the entity fields
            song.setSongTitle(songTitle);
            song.setGenre(genre);
            song.setDuration(duration);
            song.setReleaseDate(release_date);

            songService.addSongToArtist(artistId, song);

            return "redirect:/song/all";

        } catch (Exception e) {
            model.addAttribute("error", "Error saving song: " + e.getMessage());
            return "add-song";
        }
    }

    @GetMapping("/all")
    public String getAllSongs(Model model) {
        model.addAttribute("songs", songService.getAllSongs());
        return "songs-list";
    }

    // Pulling the song list for a specific artist
    @GetMapping("/artist/{artistId}")
    public String getSongsByArtist(@PathVariable Long artistId, Model model) {
        model.addAttribute("songs", songService.getSongsByArtist(artistId));
        return "songs-list";
    }
}