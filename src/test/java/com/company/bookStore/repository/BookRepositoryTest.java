package com.company.bookStore.repository;


import com.company.bookStore.model.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    void shouldGetAllBooks() {

        Book bookPeace = Book.builder()
                .id(1L).title("Peace")
                .publishedYear(2002L).build();

        Book bookIndependence =
                Book.builder()
                        .id(2L).title("India Independence")
                        .publishedYear(1998L).build();

        bookRepository.save(bookPeace);
        bookRepository.save(bookIndependence);

        Assertions.assertTrue(bookRepository.findAll().contains(bookPeace));
        Assertions.assertTrue(bookRepository.findAll().contains(bookIndependence));
    }

    @Test
    void shouldFindBookById() {
        Book bookPeace = Book.builder()
                .id(1L).title("Peace")
                .publishedYear(2002L).build();
        bookRepository.save(bookPeace);
        Assertions.assertTrue(bookRepository.findById(1L).isPresent());

    }

    @Test
    void shouldFindBooksByTitleIgnoringCase() {
        Book bookPeace = Book.builder()
                .id(1L).title("Peace")
                .publishedYear(2002L).build();
        bookRepository.save(bookPeace);
        Assertions.assertTrue(bookRepository.findBooksByTitleIgnoreCase("peace").contains(bookPeace));
    }

    @Test
    void shouldFindBookByTitleAndPublishedYear() {
        Book bookPeace = Book.builder()
                .id(1L).title("Peace")
                .publishedYear(2002L).build();
        bookRepository.save(bookPeace);
        Assertions.assertEquals(Optional.of(bookPeace),
                bookRepository.findBookByTitleAndPublishedYear("Peace", 2002L));
    }
}
