package kafka.mtls;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class KafkaConsumer {

    public static final String TOPIC = "secured-strings";

    public final List<String> messages = new ArrayList<>();

    @KafkaListener(topics = TOPIC)
    public void receive(ConsumerRecord<String, String> consumerRecord) {
        log.info("Received payload: '{}'", consumerRecord.toString());
        messages.add(consumerRecord.value());
    }

}
