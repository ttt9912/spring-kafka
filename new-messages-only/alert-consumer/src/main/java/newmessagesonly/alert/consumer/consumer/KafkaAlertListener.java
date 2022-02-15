package newmessagesonly.alert.consumer.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class KafkaAlertListener implements ConsumerSeekAware {
    public static final String TOPIC = "alerts";

    @KafkaListener(topics = TOPIC, groupId = "alerts-consumer-group", containerFactory = "allKafkaListenerContainerFactory")
    public void listen(String message, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key, @Header(KafkaHeaders.OFFSET) int offset) {
        log.info("Consumer Group 'alerts-consumer-group' received [key={}, value={}] with offset={}", key, message, offset);
    }

    @Override
    public void onPartitionsAssigned(final Map<TopicPartition, Long> assignments, final ConsumerSeekCallback callback) {
        assignments.forEach((t, o) -> callback.seekToEnd(t.topic(), t.partition()));
    }
}
