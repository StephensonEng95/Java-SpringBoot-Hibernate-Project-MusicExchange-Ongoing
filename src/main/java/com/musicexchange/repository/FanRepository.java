package com.musicexchange.repository;

import com.musicexchange.models.Fan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FanRepository extends JpaRepository<Fan, Long> {




    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<Fan> findByUsername( String username);

    @Query("Select f From Fan f LEFT JOIN FETCH f.artist WHERE f.username = :username ")
     Optional<Fan> findByUsernameWithArtists(@Param("username") String username);

}