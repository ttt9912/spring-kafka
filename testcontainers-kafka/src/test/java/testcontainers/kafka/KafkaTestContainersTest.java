package testcontainers.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;
import testcontainers.kafka.api.Country;
import testcontainers.kafka.consumer.KafkaCountryListener;
import testcontainers.kafka.producer.KafkaCountryPublisher;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
// @Import(KafkaTestContainersTest.KafkaTestContainersConfiguration.class)
@SpringBootTest
@DirtiesContext
public class KafkaTestContainersTest {

    @ClassRule
    public static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:5.4.3"));

    @Autowired
    public KafkaTemplate<String, String> template;

    @Autowired
    private KafkaCountryListener consumer;

    @Autowired
    private KafkaCountryPublisher producer;

    @Value("${test.topic}")
    private String topic;

    @Test
    public void givenKafkaDockerContainer_whenSendingtoDefaultTemplate_thenMessageReceived() throws Exception {
        template.send(topic, "Sending with default template");
        // consumer.getLatch().await(10000, TimeUnit.MILLISECONDS);
        // assertThat(consumer.getLatch().getCount(), equalTo(0L));
        // assertThat(consumer.getPayload(), containsString("embedded-test-topic"));
    }

    @Test
    public void givenKafkaDockerContainer_whenSendingtoSimpleProducer_thenMessageReceived() throws Exception {
        producer.publish(new Country("Australia", 25_000_000));
        // consumer.getLatch().await(10000, TimeUnit.MILLISECONDS);
        // assertThat(consumer.getLatch().getCount(), equalTo(0L));
        // assertThat(consumer.getPayload(), containsString("embedded-test-topic"));
    }

    @TestConfiguration
    static class KafkaTestContainersConfiguration {

        @Bean
        ConcurrentKafkaListenerContainerFactory<Integer, String> kafkaListenerContainerFactory() {
            ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
            factory.setConsumerFactory(consumerFactory());
            return factory;
        }

        @Bean
        public ConsumerFactory<Integer, String> consumerFactory() {
            return new DefaultKafkaConsumerFactory<>(consumerConfigs());
        }

        @Bean
        public Map<String, Object> consumerConfigs() {
            Map<String, Object> props = new HashMap<>();
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
            props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
            props.put(ConsumerConfig.GROUP_ID_CONFIG, "baeldung");
            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            return props;
        }

        @Bean
        public ProducerFactory<String, String> producerFactory() {
            Map<String, Object> configProps = new HashMap<>();
            configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
            configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            return new DefaultKafkaProducerFactory<>(configProps);
        }

        @Bean
        public KafkaTemplate<String, String> kafkaTemplate() {
            return new KafkaTemplate<>(producerFactory());
        }

    }
}
