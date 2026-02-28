package com.musicexchange.service;

import com.musicexchange.models.Artist;
import com.musicexchange.repository.ArtistRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class ArtistService {

    private final ArtistRepository artistRepo;
    private final PasswordEncoder passwordEncoder;

    public ArtistService(ArtistRepository artistRepo, PasswordEncoder passwordEncoder) {
        this.artistRepo = artistRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Artist> getArtistById(Long artistId) {
        return artistRepo.findById(artistId);
    }

    public void createArtist(String username, String email, String password) {
        if (artistRepo.existsByUsername(username)) {
            throw new RuntimeException("Artist username already exists.");
        }
        if (artistRepo.existsByEmail(email)) {
            throw new RuntimeException("Artist email already exists.");
        }

        Artist artist = new Artist();
        artist.setUsername(username);
        artist.setEmail(email);

        // Encodes the raw password into a BCrypt hash before saving
        artist.setPassword(passwordEncoder.encode(password));

        artistRepo.save(artist);
        log.info("Artist created successfully with ID: {} and username : {}",artist.getArtistId(), artist.getUsername());
    }

    /**
     * This is the logic used by the UserController for login.
     * It finds the user by username first, then uses the encoder
     * to verify if the raw password matches the stored hash.
     */
    public Optional<Artist> authenticateArtist(String username, String password) {
        log.debug("Attempting authentication for artist: {}", username);

        return artistRepo.findByUsername(username)
                .filter(artist -> {
                    boolean matches = passwordEncoder.matches(password, artist.getPassword());

                    if (!matches) {

                        log.warn("Authentication failed: Invalid password for artist '{}'", username);
                    }

                    return matches;
                });

    }

    public void updateArtistEmail(Long artistId, String email) {
        artistRepo.findById(artistId).ifPresent(artist -> {
            artist.setEmail(email);
            artistRepo.save(artist);
        });
        log.info("Email update successful for :{}",artistId);
    }

    public void updateArtistPassword(Long artistId, String password) {
        artistRepo.findById(artistId).ifPresent(artist -> {
            // Re-hashes the new password during an update
            artist.setPassword(passwordEncoder.encode(password));
            artistRepo.save(artist);
        });
        log.info("Password update successful for :{}",artistId);

    }

    public void deleteByArtistId(Long artistId){
        artistRepo.findById(artistId)
                .ifPresentOrElse(
                        artistRepo::delete,
                        () -> { throw new RuntimeException("Artist not found for ID: " + artistId); }
                );
        log.info("Artist deletion successful for :{}",artistId);

    }
}