package app.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class KafkaConsumerCoordinator {
    private final List<ConcurrentMessageListenerContainer> listenerContainers;

    public KafkaConsumerCoordinator(final List<ConcurrentMessageListenerContainer> listenerContainers) {
        this.listenerContainers = listenerContainers;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startConsumers() {
        listenerContainers.forEach(this::startContainer);
    }

    private void startContainer(final ConcurrentMessageListenerContainer container) {
        log.info("starting kafka consumer messageListener={} container={}", container.getContainerProperties().getMessageListener().getClass().getSimpleName(), container.toString());
        container.start();
    }
}
