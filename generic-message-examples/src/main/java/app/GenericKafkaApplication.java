package app;

import app.api.Book;
import app.api.BookKey;
import app.api.Movie;
import app.api.MovieKey;
import framework.FrameworkConfig;
import framework.consumer.KafkaConsumerCoordinator;
import framework.producer.KafkaPublisher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.EventListener;

/*
 * Not longer uses @KafkaListener, Consumers are MessageListener implementations
 */
@Import(FrameworkConfig.class)
@SpringBootApplication
public class GenericKafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(GenericKafkaApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startConsumers(final KafkaConsumerCoordinator kafkaConsumerCoordinator) {
        kafkaConsumerCoordinator.startKafkaConsumers();
    }

    @Bean
    CommandLineRunner producer(KafkaPublisher publisher) {
        return args -> {
            publisher.publishApiElement(new Book(new BookKey("Homo Faber"), "Max Frisch"));
            publisher.publishApiElement(new Book(new BookKey("Faust"), "Goethe"));
            publisher.publishApiElement(new Movie(new MovieKey("Once upon a time in Hollywood"), 2019));
            publisher.publishApiElement(new Movie(new MovieKey("Django Unchained"), 2012));
        };
    }
}
