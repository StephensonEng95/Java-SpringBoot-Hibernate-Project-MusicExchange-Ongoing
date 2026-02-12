package com.musicexchange.repository;

import com.musicexchange.models.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for Artist entities.
 * Extends JpaRepository to leverage built-in CRUD and pagination.
 */
@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    // Custom query methods derived by Spring from the method names
    Optional<Artist> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}