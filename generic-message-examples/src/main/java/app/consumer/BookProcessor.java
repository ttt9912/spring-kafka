package app.consumer;

import app.api.Book;
import app.api.BookKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BookProcessor extends KafkaProcessor<BookKey, Book> {
    @Override
    String getTopic() {
        return Book.BOOK_TOPIC;
    }
}
