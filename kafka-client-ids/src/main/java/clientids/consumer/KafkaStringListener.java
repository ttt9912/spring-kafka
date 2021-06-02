package clientids.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static clientids.topics.KafkaTopicConfig.TOPIC;

@Slf4j
@Component
public class KafkaStringListener {

    @KafkaListener(topics = TOPIC, groupId = "names-grp", containerFactory = "containerFactory")
    public void listen(final ConsumerRecord<String, String> record) {
        log.info("'names-grp' msg={} offset={}", record.value(), record.offset());
        if (record.value().equals("George")) {
            throw new RuntimeException("failed");
        }
    }
}
