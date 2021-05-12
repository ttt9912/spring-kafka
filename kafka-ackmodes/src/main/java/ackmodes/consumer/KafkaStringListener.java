package ackmodes.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/*
 * @Header arguments - optional
 *
 * containerFactory - if not specified, default containerFactory will be used
 *                  -> a consumer group with name='' (empty) will be created and used
 */
@Slf4j
@Component
public class KafkaStringListener {

    /*
     * Default AckMode: BADGE
     * - on exception, offset is committed and consuming goes on
     */
    @KafkaListener(topics = "names", groupId = "names-grp", containerFactory = "containerFactory")
    public void listen(final ConsumerRecord<String, String> record) {
        log.info("'names-grp' msg={} offset={}", record.value(), record.offset());
        if (record.value().equals("George")) {
            throw new RuntimeException("failed");
        }
    }

    /*
     * without acknowledgment.acknowledge() records are consumed but not committed
     * after restart, records are consumed again
     */
    @KafkaListener(topics = "names", groupId = "names-man-ack", containerFactory = "manualAckContainerFactory")
    public void manualAck(final ConsumerRecord<String, String> record, final Acknowledgment acknowledgment) {
        log.info("'names-man-ack' msg={} offset={}", record.value(), record.offset());
        acknowledgment.acknowledge();
    }
}
