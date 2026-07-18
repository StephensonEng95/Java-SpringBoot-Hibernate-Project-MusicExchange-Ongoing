package com.musicexchange.kafka;

/**
 * Pblished when an artist adds new song
 * Carries only IDs — not the Fan/Artist/Song entities — to avoid coupling
 * the Kafka message schema to the JPA model and prevent
 * LazyInitializationException on serialization.
 *
 * @param fanId id of fan to be notified
 * @param artistId id of artist who added song
 * @param songId id of song added
 */
public record ArtistAddedSongEvent(Long fanId, Long artistId, Long songId) {
}