package com.musicexchange.repository;

import com.musicexchange.models.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

    // Checks if song name exists before upload
    boolean existsBySongTitle(String songTitle);

    // Find a specific song by its name
    Optional<Song> findBySongTitle(String songTitle);

    // Get all songs for an artist's profile page
    List<Song> findByArtistId(Long artistId);

    // Delete by title name instead of ID
    void deleteBySongTitle(String songTitle);
}