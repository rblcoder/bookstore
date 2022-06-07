package com.company.bookStore.config;

import com.company.bookStore.model.Book;
import com.company.bookStore.model.Genre;
import com.company.bookStore.repository.BookRepository;
import com.company.bookStore.repository.GenreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;


@Configuration
public class PreLoadData {
    Logger logger = LoggerFactory.getLogger(PreLoadData.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GenreRepository genreRepository;

    public @PostConstruct
    void init() {

        Genre genreNonFiction = new Genre(1L, "Non fiction");
        logger.info("Preloading " + genreRepository.save(genreNonFiction));

        Book bookPeace = Book.builder()
                .id(1L).title("Peace")
                .publishedYear(2002L)
                .genre(genreNonFiction).build();
        logger.info("Preloading " + bookRepository.save(bookPeace));

    }

}
