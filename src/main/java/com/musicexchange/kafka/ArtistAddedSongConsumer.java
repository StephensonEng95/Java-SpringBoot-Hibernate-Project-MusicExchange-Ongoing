package com.musicexchange.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ArtistAddedSongConsumer {

    @KafkaListener(topics = "song.added", groupId = "music-exchange-group")
    public void consume (ArtistAddedSongEvent event){
        log.info("Song added event received: {}", event);
    }
}
