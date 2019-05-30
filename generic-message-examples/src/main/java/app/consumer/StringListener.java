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
public abstract class StringListener<SOURCE_KEY extends ApiKey, SOURCE extends ApiElement<SOURCE_KEY>> implements MessageListener<String, String> {

    @Override
    public void onMessage(final ConsumerRecord<String, String> record) {
        log.info("received record [topic={}, partition={}, offset={}, key={}, value={}]",
                record.topic(), record.partition(), record.offset(), record.key(), record.value());

        SOURCE entity = createAndSaveEntity(record.value());
        publish(process(entity));
    }


    private SOURCE createAndSaveEntity(final String json) {
        SOURCE entity = deserialize(json);
        save(entity);
        return entity;
    }

    private SOURCE deserialize(final String json) {
        // TODO deserialize
        return null;
    }

    private void save(final SOURCE entity) {
        // TODO save
    }

    private void publish(final ApiElement target) {
        if (target == null) {
            return;
        }
        log.info("publishing blabla");
        // TODO publish(target)
    }

    <TARGET extends ApiElement> TARGET process(SOURCE apiElement) {
        return null;
    }

    abstract String getTopic();
}
