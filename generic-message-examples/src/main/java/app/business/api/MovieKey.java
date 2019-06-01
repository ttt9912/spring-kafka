package app.business.api;

import app.framework.ApiKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieKey implements ApiKey {
    private String title;
}
