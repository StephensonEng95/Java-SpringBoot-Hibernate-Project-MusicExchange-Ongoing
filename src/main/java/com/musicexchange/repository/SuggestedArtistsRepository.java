package com.musicexchange.repository;

import com.musicexchange.models.SuggestedArtists;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuggestedArtistsRepository extends JpaRepository<SuggestedArtists, Long> {
}
