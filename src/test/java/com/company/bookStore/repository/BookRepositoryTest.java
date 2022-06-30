package com.company.bookStore.repository;


import com.company.bookStore.model.Book;
import com.company.bookStore.model.Genre;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    GenreRepository genreRepository;

    @Test
    void shouldSaveGenre() {
        Genre genreNonFiction = new Genre(1L, "Classics");
        Assertions.assertEquals(genreNonFiction, genreRepository.save(genreNonFiction));
    }

    @Test
    void shouldGetAllBooks() {

        Book bookPeace = Book.builder()
                .id(1L).title("Meditation Now")
                .publishedYear(2010L).build();

        Book bookIndependence =
                Book.builder()
                        .id(2L).title("India Independence")
                        .publishedYear(1998L).build();

        bookRepository.save(bookPeace);
        bookRepository.save(bookIndependence);
        List<Book> books = new ArrayList<>();
        books.add(bookPeace);
        books.add(bookIndependence);
        Assertions.assertEquals(books, bookRepository.findAll());

    }

    @Test
    void shouldFindBookById() {
        Genre genreNonFiction = new Genre(1L, "Classics");
        genreRepository.save(genreNonFiction);
        Book bookPeace = Book.builder()
                .id(1L).title("Jack and the beanstalk")
                .publishedYear(1992L)
                .genre(genreNonFiction).build();
        bookRepository.save(bookPeace);
        Assertions.assertEquals(Optional.of(bookPeace), bookRepository.findById(1L));

    }

    @Test
    void shouldFindBooksByTitleIgnoringCase() {
        Book bookPeace = Book.builder()
                .id(1L).title("Jack and the beanstalk")
                .publishedYear(1992L).build();
        bookRepository.save(bookPeace);
        Assertions.assertTrue(bookRepository.findBooksByTitleIgnoreCase("jack and the beanstalk").contains(bookPeace));
    }

    @Test
    void shouldFindBookByTitleAndPublishedYear() {
        Book bookJack = Book.builder()
                .id(1L).title("Jack and the beanstalk")
                .publishedYear(2002L).build();
        bookRepository.save(bookJack);
        Assertions.assertEquals(Optional.of(bookJack),
                bookRepository
                        .findBookByTitleAndPublishedYear("Jack and the beanstalk",
                                2002L));
    }

    @Test
    void shouldFindBooksByTitle(){
        Book bookJack = Book.builder()
                .id(1L).title("Jack and the beanstalk")
                .publishedYear(2002L).build();
        bookRepository.save(bookJack);
        List<Book> bookList = new ArrayList<>();
        bookList.add(bookJack);
        Assertions.assertEquals(bookList,
                bookRepository.findBooksForTitle(bookJack.getTitle()));

    }

    @Test
    void shouldfindBooksForPublishedYearYearStartEnd(){
        Book bookPeace = Book.builder()
                .id(1L).title("Meditation Now")
                .publishedYear(2010L).build();

        Book bookIndependence =
                Book.builder()
                        .id(2L).title("India Independence")
                        .publishedYear(1998L).build();

        bookRepository.save(bookPeace);
        bookRepository.save(bookIndependence);
        List<Book> resultBooks = new ArrayList<>();
        resultBooks.add(bookPeace);
        Assertions.assertEquals(resultBooks,
                bookRepository.findBooksForPublishedYearYearStartEnd(2010L, 2010L));
    }

    @Test
    void shouldfindAllBooksTitlePublishedYear() {
        Book bookPeace = Book.builder()
                .id(1L).title("Meditation Now")
                .publishedYear(2010L).build();

        bookRepository.save(bookPeace);

        List<Object[]> objectList;
        objectList = bookRepository.findAllBooksTitlePublishedYear();
        Assertions.assertEquals(bookPeace.getTitle(), objectList.get(0)[0]);
        Assertions.assertEquals(bookPeace.getPublishedYear(), objectList.get(0)[1]);
        System.out.println(objectList.get(0)[0]);
        System.out.println(objectList.get(0)[1]);

    }
}
