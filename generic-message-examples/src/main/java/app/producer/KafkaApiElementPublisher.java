package app.producer;

import app.api.ApiElement;
import app.api.ApiKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
public class KafkaApiElementPublisher {

    @Autowired
    private KafkaTemplate<ApiKey, ApiElement> kafkaTemplate;

    public void publishApiElement(final ApiElement element) {
        final ListenableFuture<SendResult<ApiKey, ApiElement>> future = kafkaTemplate.send(element.getTopic(), element.getKey(), element);

        // optional callback to be executed after send
        future.addCallback(new ListenableFutureCallback<SendResult<ApiKey, ApiElement>>() {
            @Override
            public void onSuccess(final SendResult<ApiKey, ApiElement> result) {
                log.info("Sent kafka message [topic={}, partition={}, offset={}, key={}, value={}]",
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset(),
                        result.getProducerRecord().key(),
                        result.getProducerRecord().value());
            }

            @Override
            public void onFailure(final Throwable ex) {
                log.warn("Unable to send kafka message [topic={} key={} value={}] exception: {}" +
                        element.getTopic(), element.getKey(), element, ex.getMessage());
            }
        });
    }
}
