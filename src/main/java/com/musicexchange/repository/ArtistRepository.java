package com.musicexchange.repository;

import com.musicexchange.models.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    // This method is the key to the login fix.
    // We only fetch by username because SQL cannot compare raw passwords to BCrypt hashes.
    Optional<Artist> findByUsername(String username);

    // Used during the signup process to prevent multiple accounts with the same name.
    boolean existsByUsername(String username);

    // Used during signup or profile updates to ensure every artist has a unique email.
    boolean existsByEmail(String email);

    void deleteByArtistId(Long artistId);
}