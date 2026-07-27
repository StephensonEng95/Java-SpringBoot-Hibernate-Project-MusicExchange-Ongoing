package com.musicexchange.kafka;

/**
 * Pblished when an artist adds new song
 * Carries only IDs — not the Fan/Artist/Song entities — to avoid coupling
 * the Kafka message schema to the JPA model and prevent
 * LazyInitializationException on serialization.
 *
 * @param fanId artistId of fan to be notified
 * @param artistId artistId of artist who added song
 * @param songId artistId of song added
 */
public record ArtistAddedSongEvent(Long fanId, Long artistId, Long songId) {
}