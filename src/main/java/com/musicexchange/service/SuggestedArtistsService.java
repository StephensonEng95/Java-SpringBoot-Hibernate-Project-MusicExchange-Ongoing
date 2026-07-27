package com.musicexchange.service;

import com.musicexchange.dto.SuggestedArtistsResponseDto;
import com.musicexchange.repository.SuggestedArtistsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SuggestedArtistsService {

    private final SuggestedArtistsRepository suggestedArtistRepository;

    public List<SuggestedArtistsResponseDto> getAllSuggestedArtists() {
        return suggestedArtistRepository.findAll()
                .stream()
                .map(artist -> new SuggestedArtistsResponseDto(
                        artist.getId(),
                        artist.getUsername(),
                        artist.getProfilePic(),
                        artist.getGenre()
                ))
                .toList();
    }
}