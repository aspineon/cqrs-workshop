package io.github.nowakprojects.cqrsworkshop.withoutcqrs;

import com.github.javafaker.Book;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Random;

import static io.github.nowakprojects.cqrsworkshop.ExampleConstants.BOOKS_COUNT;
import static io.github.nowakprojects.cqrsworkshop.ExampleConstants.OPINIONS_PER_BOOK_COUNT;

@Component
public class DataBootstrap {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final BookRepository bookRepository;
    private final Faker faker = new Faker(Locale.ENGLISH);
    private final Random random = new Random();


    public DataBootstrap(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void loadFakeBooks() {
        log.info("Start loading fake books");
        for (int i = 0; i < BOOKS_COUNT; i++) {
            io.github.nowakprojects.cqrsworkshop.withoutcqrs.Book book = generateBook();
            bookRepository.save(book);
        }
        log.info("Finish loading fake books");
    }

    public io.github.nowakprojects.cqrsworkshop.withoutcqrs.Book generateBook() {
        Book fakeBook = faker.book();
        io.github.nowakprojects.cqrsworkshop.withoutcqrs.Book book = new io.github.nowakprojects.cqrsworkshop.withoutcqrs.Book(
                fakeBook.title(),
                fakeBook.author(),
                fakeBook.genre()
        );

        for (int j = 0; j < OPINIONS_PER_BOOK_COUNT; j++) {
            book.addOpinion(new Opinion("Author" + j, faker.chuckNorris().fact(), random.nextInt(10)+1));
        }
        return book;
    }
}
