package app.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Delegate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book extends ApiElement<BookKey> {
    public static final String BOOK_TOPIC = "book";

    private BookKey key;
    private String author;

    @Override
    public String getTopic() {
        return BOOK_TOPIC;
    }
}
