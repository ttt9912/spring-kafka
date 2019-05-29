package app.consumer;

import app.api.Book;
import app.api.BookKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Slf4j
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
    public Class<Book> getApiElementClass() {
        return Book.class;
    }

    @Override
    public Class<BookKey> getApiKeyClass() {
        return BookKey.class;
    }
}
