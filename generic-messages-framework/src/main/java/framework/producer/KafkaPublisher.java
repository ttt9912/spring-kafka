package framework.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import framework.api.ApiElement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
public class KafkaPublisher {

    @Autowired
    private KafkaTemplate<String, String> stringKafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void publishApiElement(final ApiElement element) {
        ListenableFuture<SendResult<String, String>> future =
                stringKafkaTemplate.send(element.getTopic(), serialize(element.getKey()), serialize(element));

        future.addCallback(createCallback(element));
    }

    private String serialize(final Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing element for " + o);
        }
    }

    // optional callback
    private ListenableFutureCallback<? super SendResult<String, String>> createCallback(final ApiElement element) {
        return new ListenableFutureCallback<SendResult<String, String>>() {
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
                throw new RuntimeException(String.format("Unable to send kafka message [topic=%s key=%s value=%s] exception: %s" +
                        element.getTopic(), element.getKey(), element, ex.getMessage()));
            }
        };
    }
}
