package com.musicexchange.service;

import com.musicexchange.models.Artist;
import com.musicexchange.repository.ArtistRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Artist service layer handling business logic for artist entities
 */
@Service
@Transactional
@Slf4j
public class ArtistService {

    // Marked as final to ensure they are assigned via constructor (best practice)
    private final ArtistRepository artistRepo;
    private final PasswordEncoder passwordEncoder;

    public ArtistService(ArtistRepository artistRepo, PasswordEncoder passwordEncoder) {
        this.artistRepo = artistRepo;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Creates new artist with provided parameters at signup
     */
    public Artist createArtist(String username, String email, String rawPassword) {
        log.info("Attempting to create artist with username: {}", username);

        if(artistRepo.existsByUsername(username)) {
            log.warn("Duplicate username attempt: {}", username);
            throw new RuntimeException("Username '" + username + "' is already taken");
        }

        if(artistRepo.existsByEmail(email)) {
            log.warn("Duplicate email attempt: {}", email);
            throw new RuntimeException("Email '" + email + "' is already taken");
        }

        Artist artist = new Artist();
        artist.setUsername(username);
        artist.setEmail(email);
        // Encoding the password before saving
        artist.setPassword(passwordEncoder.encode(rawPassword));

        Artist savedArtist = artistRepo.save(artist);
        log.debug("Artist created successfully with id: {}", savedArtist.getId());
        return savedArtist;
    }

    /**
     * Authenticates an artist by checking credentials
     */
    public Optional<Artist> authenticateArtist(String username, String rawPassword) {
        log.info("Authenticating artist: {}", username);

        return artistRepo.findByUsername(username)
                .filter(artist -> passwordEncoder.matches(rawPassword, artist.getPassword()));
    }

    /**
     * Updates artist email using JPA's persistent context
     */
    public void updateArtistEmail(Long id, String email) {
        log.info("Updating email for artist id: {}", id);

        Artist artist = artistRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Can't find Artist with id: " + id));

        artist.setEmail(email);
        artistRepo.save(artist); // JPA handles the update automatically
        log.debug("Artist email updated successfully");
    }

    /**
     * Updates artist password with proper encoding
     */
    public void updateArtistPassword(Long id, String rawPassword) {
        log.info("Updating password for artist id: {}", id);

        Artist artist = artistRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Can't find Artist with id: " + id));

        // Crucial: Encode the new password
        artist.setPassword(passwordEncoder.encode(rawPassword));
        artistRepo.save(artist);
    }

    /**
     * Deletes an artist entity by id
     */
    public void deleteArtist(Long id) {
        log.info("Deleting artist with id: {}", id);

        if (!artistRepo.existsById(id)) {
            throw new RuntimeException("Cannot delete: Artist not found");
        }

        artistRepo.deleteById(id);
    }
}