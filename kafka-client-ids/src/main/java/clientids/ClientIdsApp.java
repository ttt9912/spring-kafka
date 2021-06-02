package clientids;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * ------------------------------------
 * client.id
 * ------------------------------------
 * An optional identifier of a Kafka consumer (in a consumer group) that is passed to a Kafka broker with every request.
 * The sole purpose of this is to be able to track the source of requests beyond just ip and port by allowing a logical application name to be included in Kafka logs and monitoring aggregates.
 *
 *
 *
 * Random Port
 * => can be started multiple times
 */
@SpringBootApplication
public class ClientIdsApp {

    public static void main(String[] args) {
        SpringApplication.run(ClientIdsApp.class, args);
    }
}
