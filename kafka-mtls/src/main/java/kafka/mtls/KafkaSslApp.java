package kafka.mtls;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * - Use docker-kafka-mtls
 * - copy kafka.client.keystore.jks and kafka.client.truststore.jks
 *   from /docker-kafka-mtls to /resources/client-certs
 */
@SpringBootApplication
public class KafkaSslApp {

    public static void main(String[] args) {
        SpringApplication.run(KafkaSslApp.class, args);
    }

}
