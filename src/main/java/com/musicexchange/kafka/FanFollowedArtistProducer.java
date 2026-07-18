package com.musicexchange.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class FanFollowedArtistProducer {
    private final KafkaTemplate<String,FanFollowedArtistEvent> kafkaTemplate;

    public FanFollowedArtistProducer(KafkaTemplate<String,FanFollowedArtistEvent> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish (FanFollowedArtistEvent event){
        kafkaTemplate.send("artist.followed",event);
    }
}
