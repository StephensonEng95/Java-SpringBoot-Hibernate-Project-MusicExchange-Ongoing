package com.musicexchange.controllers;

import com.musicexchange.dto.SongRequestDto;
import com.musicexchange.dto.SongResponseDto;
import com.musicexchange.exceptions.DuplicateResourceException;
import com.musicexchange.exceptions.ResourceNotFoundException;
import com.musicexchange.service.SongService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/song")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    @GetMapping("/add")
    public String showAddSongForm(Model model) {
        model.addAttribute("songRequest", new SongRequestDto());
        return "add-song";
    }

    @PostMapping("/add")
    public String createSong(@Valid @ModelAttribute("songRequest") SongRequestDto request,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            return "add-song";
        }

        try {
            songService.addSong(request);
            return "redirect:/song/all";
        } catch (ResourceNotFoundException | DuplicateResourceException e) {
            model.addAttribute("error", e.getMessage());
            return "add-song";
        } catch (Exception e) {
            model.addAttribute("error", "Error saving song: " + e.getMessage());
            return "add-song";
        }
    }

    @GetMapping("/all")
    public String getAllSongs(Model model) {
        List<SongResponseDto> songs = songService.getAllSongs();
        model.addAttribute("songs", songs);
        return "songs-list";
    }

    @GetMapping("/artist/{artistId}")
    public String getSongsByArtist(@PathVariable Long artistId, Model model) {
        List<SongResponseDto> songs = songService.getSongsByArtist(artistId);
        model.addAttribute("songs", songs);
        return "songs-list";
    }
}
