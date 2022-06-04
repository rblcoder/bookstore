package com.company.bookStore.repository;

import com.company.bookStore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findBooksByTitleIgnoreCase(String title);

    Optional<Book> findBookByTitleAndPublishedYear(String title, Long publishedYear);
}