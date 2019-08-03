package app.consumer;

import app.api.Movie;
import app.api.MovieKey;
import framework.api.ApiElement;
import framework.processor.KafkaProcessor;
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

    @Override
    protected ApiElement process(final Movie apiElement) {
        return null;
    }
}
