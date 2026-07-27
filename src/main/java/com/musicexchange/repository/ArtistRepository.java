package com.musicexchange.repository;

import com.musicexchange.models.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    Optional<Artist> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Query("SELECT a FROM Artist a LEFT JOIN FETCH a.fans WHERE a.username = :username")
    Optional<Artist> findByUsernameWithFans(@Param("username") String username);

    @Query("SELECT a FROM Artist a LEFT JOIN FETCH a.songs WHERE a.username = :username")
    Optional<Artist> findByUsernameWithSongs(@Param("username") String username);
}
