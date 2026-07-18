package com.musicexchange.kafka;

public record FanFollowedArtistEvent(Long fanId, Long artistId) {
}