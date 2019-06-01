package app.consumer;

import app.api.Book;
import app.api.BookKey;
import framework.api.ApiElement;
import framework.processor.KafkaProcessor;
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

    @Override
    protected ApiElement process(final Book apiElement) {
        return null;
    }
}
