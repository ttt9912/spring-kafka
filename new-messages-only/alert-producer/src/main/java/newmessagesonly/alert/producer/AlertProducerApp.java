package newmessagesonly.alert.producer;

import newmessagesonly.alert.producer.producer.KafkaAlertPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@EnableScheduling
@SpringBootApplication
public class AlertProducerApp {

    @Autowired
    private KafkaAlertPublisher kafkaAlertPublisher;

    public static void main(String[] args) {
        SpringApplication.run(AlertProducerApp.class, args);
    }

    @Scheduled(initialDelay = 1_000, fixedDelay = 2_000)
    public void sendAlert() {
        final String message = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE) + " do something";
        kafkaAlertPublisher.publishString(message);
    }
}
