package app.consumer;

import app.api.Movie;
import app.api.MovieKey;
import org.springframework.stereotype.Component;

@Component
public class MovieListener extends KafkaProcessor<MovieKey, Movie> {

    @Override
    String getTopic() {
        return Movie.MOVIE_TOPIC;
    }
}
