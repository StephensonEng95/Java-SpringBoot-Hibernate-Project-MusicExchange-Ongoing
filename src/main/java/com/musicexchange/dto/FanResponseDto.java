package com.musicexchange.dto;

import java.time.LocalDateTime;

public record FanResponseDto(
        Long id,
        String username,
        String email,
        String profilePic,
        LocalDateTime createdAT,
        LocalDateTime updatedAt) implements UserResponseDto {
}
