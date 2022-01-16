package testcontainers.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import testcontainers.kafka.api.Country;
import testcontainers.kafka.topics.KafkaTopicConfig;

/*
 * Messages do not have a key, therefore
 * @Header(KafkaHeaders.MESSAGE_KEY) String key is not available
 */
@Slf4j
@Component
public class KafkaCountryListener {

    @KafkaListener(topics = KafkaTopicConfig.TOPIC, containerFactory = "countryKafkaListenerContainerFactory")
    public void countryListener(Country country, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition, @Header(KafkaHeaders.OFFSET) int offset) {
        log.info("Received message [partition {}, offset={}]: {}", partition, offset, country);
    }
}
