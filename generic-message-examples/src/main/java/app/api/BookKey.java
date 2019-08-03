package app.api;

import framework.api.ApiKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookKey implements ApiKey {
    private String title;
}
