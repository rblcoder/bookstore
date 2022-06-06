package com.company.bookStore.repository;


import com.company.bookStore.model.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    void shouldGetAllBooks() {
        List<Book> bookList = new ArrayList<>();
        Book bookPeace = Book.builder()
                .id(1L).title("Peace")
                .publishedYear(2002L).build();

        Book bookIndependence =
                Book.builder()
                        .id(2L).title("India Independence")
                        .publishedYear(1998L).build();

        bookRepository.save(bookPeace);
        bookRepository.save(bookIndependence);

        bookList.add(bookPeace);
        bookList.add(bookIndependence);

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
}
