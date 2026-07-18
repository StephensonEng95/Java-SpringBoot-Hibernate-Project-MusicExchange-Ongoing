package com.musicexchange.kafka;

import org.apache.kafka.common.TopicPartition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

/**
 * Configures how Kafka consumers handle message processing failures.
 * <p>
 * Without this, a single malformed or unprocessable message ("poison pill")
 * would repeatedly fail and block the consumer from ever reading subsequent
 * healthy messages on the same partition.
 * <p>
 * Failed messages are retried a limited number of times, then routed to a
 * dead-letter topic ({@code <original-topic>.DLT}) instead of blocking the
 * consumer indefinitely, so processing of new, healthy messages can continue.
 */
@Configuration
public class KafkaConsumerConfig {

    /**
     * Retries a failed message twice, 1 second apart, before giving up and
     * publishing it to its dead-letter topic via {@link DeadLetterPublishingRecoverer}.
     * The short retry count/delay accounts for transient failures (e.g. a brief
     * downstream issue) without holding up the consumer for long on messages
     * that are genuinely unprocessable.
     */
    @Bean
    public DefaultErrorHandler errorHandler(KafkaTemplate<String, Object> kafkaTemplate) {
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate,
                (record, exception) -> new TopicPartition(record.topic() + ".DLT", record.partition()));

        return new DefaultErrorHandler(recoverer, new FixedBackOff(1000L, 2L));
    }

    /**
     * Wires the {@link #errorHandler} into the listener container factory so
     * every {@code @KafkaListener} in the app picks up the retry/dead-letter
     * behaviour automatically, without needing to configure it per-listener.
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(
            ConsumerFactory<String, Object> consumerFactory, DefaultErrorHandler errorHandler) {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setCommonErrorHandler(errorHandler);
        return factory;
    }
}