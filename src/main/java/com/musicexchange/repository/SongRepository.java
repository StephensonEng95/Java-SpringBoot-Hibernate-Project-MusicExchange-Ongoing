package com.musicexchange.repository;

import com.musicexchange.models.Song;
import com.musicexchange.models.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

    List<Song> findByArtist(Artist artist);

    boolean existsBySongTitleAndArtist(String songTitle, Artist artist);

    @Query("SELECT s FROM Song s LEFT JOIN FETCH s.artist WHERE s.songTitle = :title")
    Optional<Song> findByTitleWithArtist(@Param("title") String title);
}