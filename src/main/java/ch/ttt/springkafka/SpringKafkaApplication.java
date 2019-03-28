package ch.ttt.springkafka;

import ch.ttt.springkafka.producer.KafkaStringPublisher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/*
 * Publishes/Consumer String values
 * - uses KafkaTemplate<String, String>
 * - Consumer/Producer use StringSerializer
 */
@SpringBootApplication
public class SpringKafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringKafkaApplication.class, args);
    }


    @Bean
    CommandLineRunner producer(KafkaStringPublisher publisher) {
        return args -> {
            publisher.publishString("strings", "Hello");
            publisher.publishString("strings", "World");
            publisher.publishString("strings", "Kafka");
            publisher.publishString("strings", "Rocks");
        };
    }
}
