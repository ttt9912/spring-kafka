package springkafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/*
 * @Header arguments - optional
 *
 * containerFactory - if not specified, default containerFactory will be used
 *                  -> a consumer group with name='' (empty) will be created and used
 */
@Slf4j
@Component
public class KafkaStringListener {

    @KafkaListener(topics = "strings", groupId = "strings-all", containerFactory = "allKafkaListenerContainerFactory")
    public void listen(String message, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key, @Header(KafkaHeaders.OFFSET) int offset) {
        log.info("Consumer Group 'strings-all' received [key={}, value={}] with offset={}", key, message, offset);
    }

    // Listen to partition and include Header
    @KafkaListener(topicPartitions = @TopicPartition(topic = "strings", partitions = {"0"}), containerFactory = "partitionKafkaListenerContainerFactory")
    public void listenToParition(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition, @Header(KafkaHeaders.OFFSET) int offset) {
        log.info("Consumer Group 'strings-partitions' received {} from partition {} with offset={}", message, partition, offset);
    }

    // Messages are filtered
    @KafkaListener(topics = "strings", groupId = "strings-filtered", containerFactory = "filterKafkaListenerContainerFactory")
    public void listenWithFilter(String message) {
        log.info("Consumer Group 'strings-filtered' received {}", message);
    }
}
