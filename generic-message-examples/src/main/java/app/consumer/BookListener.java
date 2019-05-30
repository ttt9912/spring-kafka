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
public class BookListener extends StringListener<BookKey, Book> {
    @Override
    String getTopic() {
        return Book.BOOK_TOPIC;
    }
}
