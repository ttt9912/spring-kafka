package springkafka;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springkafka.api.Greeting;
import springkafka.producer.KafkaGreetingPublisher;

/*
 * Kafka with custom objects requires configuring
 * - serializer in ProducerFactory
 * - deserializer in ConsumerFactory
 */
@SpringBootApplication
public class ObjectKafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ObjectKafkaApplication.class, args);
    }

    @Bean
    CommandLineRunner producer(KafkaGreetingPublisher publisher) {
        return args -> {
            publisher.publishGreeting(new Greeting("Hello", "English"));
            publisher.publishGreeting(new Greeting("Bonsoir", "French"));
            publisher.publishGreeting(new Greeting("Hola", "Spanish"));
            publisher.publishGreeting(new Greeting("Hallo", "German"));
        };
    }
}
