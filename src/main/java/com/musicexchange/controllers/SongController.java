package com.musicexchange.controllers;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.musicexchange.models.Song;
import com.musicexchange.service.SongService;

/**
 * Song Controller handling song CRUD operations
 */

@Controller
@RequestMapping("/song")
public class SongController { 
    
    private final SongService songService;
    
    public SongController(SongService songService) {  
        this.songService = songService; 
    }

    // Show form to add new song
    @GetMapping("/add")
    public String showAddSongForm(Model model) {
        model.addAttribute("song", new Song());
        return "song"; // Matches song.html
    }

    // Process song creation
    @PostMapping("/add")
    public String createSong(@RequestParam String songTitle,
                            @RequestParam String genre,
                            @RequestParam int duration,
                            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate release_date,
                            @RequestParam String artistUserame,
                            Model model) {
        try {
            Song song = songService.addSong(songTitle, genre,duration,release_date, artistUsername);
            model.addAttribute("message", "Song '" + songTitle + "' added successfully!");
            return "redirect:/song/all";
            
        } catch (Exception e) {
            model.addAttribute("error", "Error adding song: " + e.getMessage());
            return "song";
        }   
    }

    @GetMapping("/all")
    public String getAllSongs(Model model) {
        model.addAttribute("songs", songService.getAllSongs());
        return "song"; 
    }

    @GetMapping("/artist/{artistid}")
    public String getSongsByArtist(@PathVariable Long artistid, Model model) {
        model.addAttribute("songs", songService.getSongsByArtistId(artistid));
        return "song"; 
    }
}
