package springkafka;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springkafka.producer.KafkaStringPublisher;

/*
 * Publishes/Consumer String values
 * - uses KafkaTemplate<String, String>
 * - Consumer/Producer use StringSerializer
 */
@SpringBootApplication
public class StringKafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(StringKafkaApplication.class, args);
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
