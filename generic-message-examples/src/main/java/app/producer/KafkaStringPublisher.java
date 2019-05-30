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
public class KafkaStringPublisher {

    @Autowired
    private KafkaTemplate<String, String> stringKafkaTemplate;

    public void publishApiElement(final ApiElement element) {
        ListenableFuture<SendResult<String, String>> future =
                // TODO serialize
                stringKafkaTemplate.send(element.getTopic(), element.getKey().toString(), element.toString());

        // optional callback to be executed after send
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(final SendResult<String, String> result) {
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
