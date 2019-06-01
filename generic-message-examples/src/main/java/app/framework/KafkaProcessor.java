package app.framework;

import lombok.extern.slf4j.Slf4j;

/*
 * MessageListener - alternative to @KafkaListener
 */
@Slf4j
public abstract class KafkaProcessor<SOURCE_KEY extends ApiKey, SOURCE extends ApiElement<SOURCE_KEY>> extends KafkaStringMessageListener<SOURCE_KEY, SOURCE> {

    @Override
    public void onApiElement(final SOURCE apiElement) {
        save(apiElement);
        publish(process(apiElement));
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

    protected abstract String getTopic();
}
