package app.business.api;

import app.framework.ApiKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookKey implements ApiKey {
    private String title;
}
