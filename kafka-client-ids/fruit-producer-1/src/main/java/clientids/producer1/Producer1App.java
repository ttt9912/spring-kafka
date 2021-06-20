package clientids.producer1;

import clientids.producer1.producer.KafkaStringPublisher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static clientids.producer1.topics.KafkaTopicConfig.TOPIC;

@SpringBootApplication
public class Producer1App {

    public static void main(String[] args) {
        SpringApplication.run(Producer1App.class, args);
    }

    @Bean
    public CommandLineRunner publish(KafkaStringPublisher publisher) {
        return args -> {
            publisher.publishString(TOPIC, "Pineapple");
            publisher.publishString(TOPIC, "Apple");
            publisher.publishString(TOPIC, "Watermelon");
            publisher.publishString(TOPIC, "Lime");
            publisher.publishString(TOPIC, "Avocado");
            publisher.publishString(TOPIC, "Banana");
            publisher.publishString(TOPIC, "Lemon");
            publisher.publishString(TOPIC, "Cherry");
        };
    }

}
