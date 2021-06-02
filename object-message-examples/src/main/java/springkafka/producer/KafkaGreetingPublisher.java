package springkafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import springkafka.api.Greeting;
import springkafka.topics.KafkaTopicConfig;

/*
 * By default, key=null
 */
@Slf4j
@Component
public class KafkaGreetingPublisher {

    @Autowired
    private KafkaTemplate<String, Greeting> greetingKafkaTemplate;

    public void publishGreeting(final Greeting greeting) {
        ListenableFuture<SendResult<String, Greeting>> future = greetingKafkaTemplate.send(KafkaTopicConfig.TOPIC, greeting);

        // optional callback to be executed after send
        future.addCallback(new ListenableFutureCallback<SendResult<String, Greeting>>() {
            @Override
            public void onSuccess(final SendResult<String, Greeting> result) {
                log.info("Sent greeting='" + greeting + "'"
                        + " with key=" + result.getProducerRecord().key()
                        + " to [topic=" + result.getRecordMetadata().topic()
                        + ", partition=" + result.getRecordMetadata().partition()
                        + ", offset=" + result.getRecordMetadata().offset() + "]");

            }

            @Override
            public void onFailure(final Throwable ex) {
                log.info("Unable to send message=[" + greeting + "] due to : " + ex.getMessage());
            }
        });
    }
}
