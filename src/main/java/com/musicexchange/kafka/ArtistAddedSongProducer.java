package com.musicexchange.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ArtistAddedSongProducer {
    private final KafkaTemplate<String, ArtistAddedSongEvent> kafkaTemplate;

    public ArtistAddedSongProducer(KafkaTemplate<String, ArtistAddedSongEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public void publish(ArtistAddedSongEvent event) {
        kafkaTemplate.send("song.added",event);
    }
}