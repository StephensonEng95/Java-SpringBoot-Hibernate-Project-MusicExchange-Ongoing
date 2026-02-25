package com.musicexchange.repository;

import com.musicexchange.models.Song;
import com.musicexchange.models.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

    // Retrieve songs that belong to a specific category.
    List<Song> findByGenre(String genre);

    // Find songs that match the exact title provided.
    List<Song> findBySongTitle(String songTitle);

    // Fetch songs associated with a specific Artist entity.
    List<Song> findByArtist(Artist artist);
}