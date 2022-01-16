package testcontainers.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import testcontainers.kafka.api.Country;
import testcontainers.kafka.producer.KafkaCountryPublisher;

/*
 * start docker kafka-single-node
 *
 * Kafdrop: localhost:9000
 */
@EnableScheduling
@SpringBootApplication
public class App {

    @Autowired
    KafkaCountryPublisher publisher;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Scheduled(initialDelay = 5_000, fixedDelay = 60_000)
    public void publish() {
        publisher.publish(new Country("Switzerland", 8_000_000));
        publisher.publish(new Country("USA", 300_000_000));
        publisher.publish(new Country("Germany", 80_000_000));
    }
}
