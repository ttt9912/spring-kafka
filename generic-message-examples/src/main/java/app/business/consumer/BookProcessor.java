package app.business.consumer;

import app.business.api.Book;
import app.business.api.BookKey;
import app.framework.KafkaProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BookProcessor extends KafkaProcessor<BookKey, Book> {

    @Override
    protected Class<Book> getApiElementType() {
        return Book.class;
    }

    @Override
    protected String getTopic() {
        return Book.BOOK_TOPIC;
    }
}
