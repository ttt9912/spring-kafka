package ch.ttt.springkafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.HashMap;

/*
 * @EnableKafka - enable detection of @KafkaListener
 * annotation on spring managed beans
 *
 * ConsumerFactory, KafkaListenerContainerFactory - required
 * for consuming messages
 */
@Slf4j
@Configuration
@EnableKafka
public class KafkaStringConsumerConfig {

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

    @Value("${kafka.host}")
    private String bootstrapAddress;

    private ConsumerFactory<String, String> stringConsumerFactory(final String groupId) {
        HashMap<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> allKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(stringConsumerFactory("strings-all"));
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> partitionKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(stringConsumerFactory("strings-partitions"));
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> filterKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(stringConsumerFactory("strings-filtered"));
        factory.setRecordFilterStrategy(record -> record.value()
                .contains("Hello"));
        return factory;
    }
}
