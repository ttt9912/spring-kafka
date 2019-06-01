package framework.processor;

import framework.api.ApiElement;
import framework.api.ApiKey;
import framework.consumer.KafkaListener;
import framework.producer.KafkaPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/*
 * MessageListener - alternative to @KafkaListener
 */
@Slf4j
public abstract class KafkaProcessor<SOURCE_KEY extends ApiKey, SOURCE extends ApiElement<SOURCE_KEY>> extends KafkaListener<SOURCE_KEY, SOURCE> {

    @Autowired
    private KafkaPublisher publisher;

    @Override
    public void onApiElement(final SOURCE source) {
        final ApiElement target = process(source);
        publish(target);
    }

    private void publish(final ApiElement target) {
        if (target != null) {
            publisher.publishApiElement(target);
        }
    }

    protected abstract String getTopic();

    protected abstract ApiElement process(SOURCE apiElement);
}
