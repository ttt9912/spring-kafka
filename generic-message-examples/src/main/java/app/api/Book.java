package app.api;

import framework.api.ApiElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book implements ApiElement<BookKey> {
    public static final String BOOK_TOPIC = "book";

    private BookKey key;
    private String author;

    @Override
    public String getTopic() {
        return BOOK_TOPIC;
    }
}
