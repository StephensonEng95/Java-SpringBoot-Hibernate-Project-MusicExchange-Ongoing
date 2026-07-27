package com.musicexchange.kafka;

/**
 * Published when a fan follows an artist.
 * Carries only IDs — not the Fan/Artist entities — to avoid coupling
 * the Kafka message schema to the JPA model and prevent
 * LazyInitializationException on serialization.
 *
 * @param fanId    artistId of the fan who followed
 * @param artistId artistId of the artist being followed
 */

public record FanFollowedArtistEvent(Long fanId, Long artistId) {
}