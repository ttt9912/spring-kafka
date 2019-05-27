package app.consumer;

import app.api.ApiElement;
import app.api.Movie;
import app.api.MovieKey;
import org.springframework.stereotype.Component;

@Component
public class MovieListener extends ApiElementListener<MovieKey, Movie> {

    @Override
    void consume(final Movie apiElement) {
    }

    @Override
    Movie process(final Movie apiElement) {
        return null;
    }

    @Override
    String getTopic() {
        return Movie.MOVIE_TOPIC;
    }

    @Override
    public Class getApiElementClass() {
        return Movie.class;
    }

    @Override
    public Class getApiKeyClass() {
        return MovieKey.class;
    }
}
