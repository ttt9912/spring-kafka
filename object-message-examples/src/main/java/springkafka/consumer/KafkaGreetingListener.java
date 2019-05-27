package springkafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import springkafka.api.Greeting;

/*
 * Messages do not have a key, therefore
 * @Header(KafkaHeaders.MESSAGE_KEY) String key is not available
 */
@Slf4j
@Component
public class KafkaGreetingListener {

    @KafkaListener(topics = Greeting.GREETING_TOPIC, containerFactory = "greetingKafkaListenerContainerFactory")
    public void greetingListener(Greeting greeting, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition, @Header(KafkaHeaders.OFFSET) int offset) {
        log.info("Consumer Group 'greeting-group' received {} from partition {} with offset={}", greeting, partition, offset);
    }
}
