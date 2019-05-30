package app.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@EnableKafka
public class KafkaStringConsumerConfig implements ApplicationContextAware {

    @Value("${kafka.host}")
    private String bootstrapAddress;

    ApplicationContext ctx;

    private ConsumerFactory<String, String> stringConsumerFactory(final String groupId) {
        HashMap<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public void setupKafkaListener() {
        Map<String, KafkaStringMessageListener> listeners = ctx.getBeansOfType(KafkaStringMessageListener.class);

        listeners.values().forEach(listener -> {
                    ContainerProperties containerProperties = new ContainerProperties(listener.getTopic());
                    containerProperties.setMessageListener(listener);
                    ConcurrentMessageListenerContainer container = new ConcurrentMessageListenerContainer<>(stringConsumerFactory("default-group"), containerProperties);
                    container.start();
                }
        );
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}
