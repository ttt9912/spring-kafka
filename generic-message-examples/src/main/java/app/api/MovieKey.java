package app.api;

import lombok.Value;

@Value
public class MovieKey implements ApiKey{
    private String title;
}
