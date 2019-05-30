package app.consumer;

import app.api.ApiElement;
import app.api.ApiKey;
import lombok.extern.slf4j.Slf4j;

/*
 * MessageListener - alternative to @KafkaListener
 */
@Slf4j
public abstract class KafkaProcessor<SOURCE_KEY extends ApiKey, SOURCE extends ApiElement<SOURCE_KEY>> extends KafkaStringMessageListener<SOURCE_KEY, SOURCE> {

    @Override
    void onEntity(final SOURCE entity) {
        save(entity);
        publish(process(entity));
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
