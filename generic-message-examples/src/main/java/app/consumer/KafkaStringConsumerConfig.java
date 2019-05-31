package app.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@EnableKafka
public class KafkaStringConsumerConfig {

    @Value("${kafka.host}")
    private String bootstrapAddress;

    @Bean
    public ConsumerFactory<String, String> consumerFactory(@Value("${kafka.consumer.group.id}") final String groupId) {
        HashMap<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory(final ConsumerFactory<String, String> consumerFactory) {
        final ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    // combine MessageListeners & ListenerContainers
    @Bean
    public List<ConcurrentMessageListenerContainer> listenerContainers(final List<KafkaStringMessageListener> messageListeners, KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> containerFactory) {
        return messageListeners.stream()
                .map(messageListener -> createListenerContainer(containerFactory, messageListener))
                .collect(Collectors.toList());
    }

    private ConcurrentMessageListenerContainer<String, String> createListenerContainer(final KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> containerFactory, final KafkaStringMessageListener messageListener) {
        ConcurrentMessageListenerContainer<String, String> container = containerFactory.createContainer(messageListener.getTopic());
        container.getContainerProperties().setMessageListener(messageListener);
        return container;
    }
}
