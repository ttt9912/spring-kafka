package app.consumer;

import app.api.ApiElement;
import app.api.ApiKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;

/*
 * MessageListener - alternative to @KafkaListener
 */
@Slf4j
public abstract class KafkaStringMessageListener<SOURCE_KEY extends ApiKey, SOURCE extends ApiElement<SOURCE_KEY>> implements MessageListener<String, String> {

    @Override
    public void onMessage(final ConsumerRecord<String, String> record) {
        log.info("received record [topic={}, partition={}, offset={}, key={}, value={}]",
                record.topic(), record.partition(), record.offset(), record.key(), record.value());

        SOURCE entity = deserialize(record.value());
        onEntity(entity);
    }

    private SOURCE deserialize(final String json) {
        return null; // TODO
    }

    abstract void onEntity(final SOURCE entity);

    abstract String getTopic();
}
