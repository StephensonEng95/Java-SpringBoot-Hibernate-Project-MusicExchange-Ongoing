package com.musicexchange.dto;

public record TokenResponseDto(
        String accessToken,
        String refreshToken
) {
}
