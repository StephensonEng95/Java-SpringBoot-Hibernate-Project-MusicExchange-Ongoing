package com.musicexchange.dto;

import java.time.LocalDateTime;

public record ArtistResponseDto(Long artistId, String username, String email,String profilePic, LocalDateTime createdAT, LocalDateTime updatedAt) {
}
