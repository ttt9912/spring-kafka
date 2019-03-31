package springkafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StringListener {

    @KafkaListener(topics = "strings", groupId = "strings-all", containerFactory = "allKafkaListenerContainerFactory")
    public void listen(String message) {
        log.info("Consumer Group 'strings-all' received {}", message);
    }

    // Listen to partition and include Header
    @KafkaListener(topicPartitions = @TopicPartition(topic = "strings", partitions = {"0"}))
    public void listenToParition(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info("Consumer Group 'strings-partitions' received {} from partition {}", message, partition);
    }

    // Messages are filtered
    @KafkaListener(topics = "strings", groupId = "strings-filtered", containerFactory = "filterKafkaListenerContainerFactory")
    public void listenWithFilter(String message) {
        log.info("Consumer Group 'strings-filtered' received {}", message);
    }
}
