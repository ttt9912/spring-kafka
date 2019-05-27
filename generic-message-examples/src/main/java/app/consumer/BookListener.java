package app.consumer;

import app.api.Book;
import app.api.BookKey;
import org.springframework.stereotype.Component;

@Component
public class BookListener extends ApiElementListener<BookKey, Book> {


    @Override
    void consume(final Book apiElement) {
    }

    @Override
    Book process(final Book apiElement) {
        return null;
    }

    @Override
    String getTopic() {
        return Book.BOOK_TOPIC;
    }

    @Override
    public Class getApiElementClass() {
        return Book.class;
    }

    @Override
    public Class getApiKeyClass() {
        return BookKey.class;
    }
}
