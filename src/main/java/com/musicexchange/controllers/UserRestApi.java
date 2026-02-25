package com.musicexchange.controllers;

import com.musicexchange.models.Artist;
import com.musicexchange.service.ArtistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserRestApi {

    private final ArtistService artistService;

    // Use constructor injection to get our service
    public UserRestApi(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/artist/{id}")
    public ResponseEntity<Artist> getArtist(@PathVariable Long id) {
        // Try to find the artist and return 404 if they don't exist
        return artistService.getArtistById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.debug("Artist with ID {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        // 1. Calls service to check credentials
        Optional<Artist> artist = artistService.authenticateArtist(username, password);

        if (artist.isPresent()) {
            log.info("API Login successful for: {}", username);
            // Returns the Artist object with a 200 OK status
            return ResponseEntity.ok(artist.get());
        } else {
            log.warn("API Login failed for user: {}", username);
            //Returns a simple String message with a 401 Unauthorized status
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password.");
        }
    }


    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody Artist artist) {
        try {
            // Send the raw data to the service so it can handle hashing the password
            artistService.createArtist(artist.getUsername(), artist.getEmail(), artist.getPassword());

            log.info("New artist registered via API: {}", artist.getUsername());
            return ResponseEntity.ok("Artist registered successfully.");
        } catch (Exception e) {
            // If the service throws an error (like a duplicate email), log it and return 400
            log.error("API Signup error: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Signup failed: " + e.getMessage());
        }
    }
}
