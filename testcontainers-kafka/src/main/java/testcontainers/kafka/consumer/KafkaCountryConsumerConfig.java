package testcontainers.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import testcontainers.kafka.api.Country;

import java.util.HashMap;

/*
 * Include Deserializer into ConsumerFactory
 */
@Slf4j
@Configuration
@EnableKafka
public class KafkaCountryConsumerConfig {

    @Value("${kafka.host}")
    private String bootstrapAddress;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Country> countryKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Country> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(countryConsumerFactory("country-group"));
        return factory;
    }

    private ConsumerFactory<String, Country> countryConsumerFactory(final String groupId) {
        HashMap<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return new DefaultKafkaConsumerFactory<>(props,
                new StringDeserializer(),
                new JsonDeserializer<>(Country.class));
    }
}
