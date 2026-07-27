package com.musicexchange.dto;

public record SuggestedArtistsResponseDto(
        Long id,
        String username,
        String profilePic,
        String genre
) {}