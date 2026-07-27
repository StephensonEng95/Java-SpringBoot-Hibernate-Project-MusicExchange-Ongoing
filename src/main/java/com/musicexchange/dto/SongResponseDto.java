package com.musicexchange.dto;

import java.time.LocalDateTime;

public record SongResponseDto(Long id, String title, String genre, String coverSrt, Long duration, LocalDateTime releasedAt, Long artistId) {

    public String getFormattedDuration() {
        if (duration == null || duration == 0) return "0:00";
        long minutes = duration / 60;
        long seconds = duration % 60;
        return String.format("%d:%02d", minutes, seconds);
    }
}
