package framework.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import framework.api.ApiElement;
import framework.api.ApiKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.MessageListener;

import java.io.IOException;

/*
 * MessageListener - alternative to @KafkaListener
 */
@Slf4j
public abstract class KafkaListener<SOURCE_KEY extends ApiKey, SOURCE extends ApiElement<SOURCE_KEY>> implements MessageListener<String, String> {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onMessage(final ConsumerRecord<String, String> record) {
        log.info("KafkaListener >> received record [topic={}, partition={}, offset={}, key={}, value={}]",
                record.topic(), record.partition(), record.offset(), record.key(), record.value());

        SOURCE apiElement = deserialize(record.value());
        onApiElement(apiElement);
    }

    private SOURCE deserialize(final String json) {
        try {
            return objectMapper.readValue(json, getApiElementType());
        } catch (IOException e) {
            throw new RuntimeException("Error deserializing payload for " + json);
        }
    }

    protected abstract Class<SOURCE> getApiElementType();

    protected abstract void onApiElement(final SOURCE entity);

    protected abstract String getTopic();
}
