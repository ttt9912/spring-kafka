package clientids.consumer1.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaStringListener {

    @KafkaListener(topics = "fruits", clientIdPrefix = "client1", containerFactory = "containerFactory")
    public void listen(final ConsumerRecord<String, String> record) {
        log.info("'names-grp' msg={} offset={}", record.value(), record.offset());
        if (record.value().equals("George")) {
            throw new RuntimeException("failed");
        }
    }
}
