package com.musicexchange.kafka;

public record ArtistAddedSongEvent(Long fanId, Long artistId, Long songId) {
}