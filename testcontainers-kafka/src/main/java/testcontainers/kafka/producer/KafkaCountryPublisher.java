package testcontainers.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import testcontainers.kafka.api.Country;
import testcontainers.kafka.topics.KafkaTopicConfig;

/*
 * By default, key=null
 */
@Slf4j
@Component
public class KafkaCountryPublisher {

    @Autowired
    private KafkaTemplate<String, Country> countryKafkaTemplate;

    public void publish(final Country country) {
        ListenableFuture<SendResult<String, Country>> future = countryKafkaTemplate.send(KafkaTopicConfig.TOPIC, country);

        // optional callback to be executed after send
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(final SendResult<String, Country> result) {
                log.info("Sent='" + country + "'"
                        + " with key=" + result.getProducerRecord().key()
                        + " to [topic=" + result.getRecordMetadata().topic()
                        + ", partition=" + result.getRecordMetadata().partition()
                        + ", offset=" + result.getRecordMetadata().offset() + "]");

            }

            @Override
            public void onFailure(final Throwable ex) {
                log.info("Unable to send message=[" + country + "] due to : " + ex.getMessage());
            }
        });
    }
}
