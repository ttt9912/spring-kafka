package app.topics;

import app.api.Book;
import app.api.Movie;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

/*
 * AdminClient - create multiple topics programmatically
 */
@Configuration
public class KafkaTopicsConfig {

    @Value("${kafka.host}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic bookTopic() {
        return new NewTopic(Book.BOOK_TOPIC, 3, (short) 1);
    }

    @Bean
    public NewTopic movieTopic() {
        return new NewTopic(Movie.MOVIE_TOPIC, 3, (short) 1);
    }
}
