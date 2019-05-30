package app.consumer;

import app.api.ApiElement;
import app.api.ApiKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;

/*
 * MessageListener - alternative to @KafkaListener
 */
@Slf4j
public abstract class StringListener<KEY extends ApiKey, VALUE extends ApiElement<KEY>> implements MessageListener<String, String> {

    @Override
    public void onMessage(final ConsumerRecord<String, String> record) {
        log.info("received record [topic={}, partition={}, offset={}, key={}, value={}]",
                record.topic(), record.partition(), record.offset(), record.key(), record.value());


        // createEntity(record.value()) -> TODO deserialize
        // consume(record.value());
    }

    //abstract void consume(VALUE apiElement);

    //abstract VALUE process(VALUE apiElement);

    abstract String getTopic();
}
