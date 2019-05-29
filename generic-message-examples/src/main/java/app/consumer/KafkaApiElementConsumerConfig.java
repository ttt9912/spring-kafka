package app.consumer;

import app.api.ApiElement;
import app.api.ApiKey;
import app.api.Book;
import app.api.BookKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@EnableKafka
public class KafkaApiElementConsumerConfig implements ApplicationContextAware {

    @Value("${kafka.host}")
    private String bootstrapAddress;


//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<ApiKey, ApiElement> apiElementKafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<ApiKey, ApiElement> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(apiElementConsumerFactory("api-group"));
//        return factory;
//    }
//
//    private ConsumerFactory<ApiKey, ApiElement> apiElementConsumerFactory(final String groupId) {
//        final HashMap<String, Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
//        return new DefaultKafkaConsumerFactory<>(props,
//                new JsonDeserializer<>(ApiKey.class),
//                new JsonDeserializer<>(ApiElement.class));
//    }

    ApplicationContext ctx;

    @Bean
    public void setupKafkaListener() {
        // Create configuration for your consumer. This is most basic configuration,
// you probably want to add something more, like 'auto.offset.reset',
// see: https://kafka.apache.org/documentation/#consumerconfigs
        Map<String, Object> consumerConfig = new HashMap<>();
        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, "local-groupId");

// create KafkaConsumerFactory which adds information about parsing key and value
// you could also do it in config but this is compile-safe approach


// you also need container which has info about topic and what to do with messages


        Map<String, ApiElementListener> listeners = ctx.getBeansOfType(ApiElementListener.class);

        listeners.values().forEach(listener -> {
                    DefaultKafkaConsumerFactory<ApiKey, ApiElement> kafkaConsumerFactory = new DefaultKafkaConsumerFactory<>(consumerConfig,
                            new JsonDeserializer<ApiKey>(listener.getApiKeyClass()),
                            new JsonDeserializer<ApiElement>(listener.getApiElementClass()));
                    ContainerProperties containerProperties = new ContainerProperties(listener.getTopic());
                    containerProperties.setMessageListener(listener);
                    ConcurrentMessageListenerContainer container = new ConcurrentMessageListenerContainer<>(kafkaConsumerFactory, containerProperties);
                    container.start();
                }
        );
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}
