package app.business.consumer;

import app.business.api.Movie;
import app.business.api.MovieKey;
import app.framework.KafkaProcessor;
import org.springframework.stereotype.Component;

@Component
public class MovieProcessor extends KafkaProcessor<MovieKey, Movie> {

    @Override
    protected Class<Movie> getApiElementType() {
        return Movie.class;
    }

    @Override
    protected String getTopic() {
        return Movie.MOVIE_TOPIC;
    }
}
