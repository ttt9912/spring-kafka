package clientids.producer1.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
public class KafkaStringPublisher {

    @Autowired
    private KafkaTemplate<String, String> stringKafkaTemplate;

    public void publishString(final String topic, final String message) {
        stringKafkaTemplate.send(topic, String.valueOf(message.hashCode()), message).addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(final SendResult<String, String> result) {
                log.info("Sent message='" + message + "'"
                        + " with key=" + result.getProducerRecord().key()
                        + " to [topic=" + result.getRecordMetadata().topic()
                        + ", partition=" + result.getRecordMetadata().partition()
                        + ", offset=" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(final Throwable throwable) {
                log.info("Unable to send message=[" + message + "] due to : " + throwable.getMessage());
            }
        });
    }
}
