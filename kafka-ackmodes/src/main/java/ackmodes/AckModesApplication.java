package ackmodes;

import ackmodes.producer.KafkaStringPublisher;
import ackmodes.topics.KafkaTopicConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AckModesApplication {

    public static void main(String[] args) {
        SpringApplication.run(AckModesApplication.class, args);
    }

    @Bean
    CommandLineRunner producer(KafkaStringPublisher publisher) {
        return args -> {
            Thread.sleep(5000);
            publisher.publishString(KafkaTopicConfig.TOPIC, "John");
            Thread.sleep(1000);
            publisher.publishString(KafkaTopicConfig.TOPIC, "Paul");
            Thread.sleep(1000);
            publisher.publishString(KafkaTopicConfig.TOPIC, "George");
            Thread.sleep(1000);
            publisher.publishString(KafkaTopicConfig.TOPIC, "Ringo");
        };
    }
}
