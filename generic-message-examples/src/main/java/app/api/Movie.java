package app.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Delegate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie extends ApiElement<MovieKey> {
    public static final String MOVIE_TOPIC = "movie";

    private MovieKey key;
    private Integer year;

    @Override
    public String getTopic() {
        return MOVIE_TOPIC;
    }
}
