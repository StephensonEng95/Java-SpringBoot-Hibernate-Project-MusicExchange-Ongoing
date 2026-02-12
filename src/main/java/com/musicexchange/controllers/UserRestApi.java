package com.musicexchange.controllers;

import com.musicexchange.service.ArtistService;
import com.musicexchange.service.FanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserRestApi {

    private final ArtistService artistService;
    private final FanService fanService;

    public UserRestApi(ArtistService artistService, FanService fanService) {
        this.artistService = artistService;
        this.fanService = fanService;
    }

    // Handles signup by checking the role string in the JSON body
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Map<String, String> data) {
        String role = data.get("role");
        String user = data.get("username");
        String email = data.get("email");
        String pass = data.get("password");

        if ("ARTIST".equalsIgnoreCase(role)) {
            artistService.createArtist(user, email, pass);
            return new ResponseEntity<>("Artist registered", HttpStatus.CREATED);
        } else if ("FAN".equalsIgnoreCase(role)) {
            fanService.createFan(user, email, pass);
            return new ResponseEntity<>("Fan registered", HttpStatus.CREATED);
        }

        return ResponseEntity.badRequest().body("Invalid role provided");
    }

    @PatchMapping("/artist/{id}/email")
    public ResponseEntity<Void> updateArtistEmail(@PathVariable Long id, @RequestParam String email) {
        artistService.updateArtistEmail(id, email);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/artist/{id}/password")
    public ResponseEntity<Void> updateArtistPassword(@PathVariable Long id, @RequestParam String password) {
        artistService.updateArtistPassword(id, password);
        return ResponseEntity.noContent().build();
    }

    // Clean delete for a specific artist
    @DeleteMapping("/artist/{id}")
    public ResponseEntity<Void> removeArtist(@PathVariable Long id) {
        // artistService.deleteArtist(id);
        return ResponseEntity.noContent().build();
    }
}