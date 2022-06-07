package com.company.bookStore.config;

import com.company.bookStore.model.Book;
import com.company.bookStore.repository.BookRepository;
import com.company.bookStore.service.BookDto;
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

    public @PostConstruct
    void init() {

        Book bookPeace = Book.builder()
                .id(1L).title("Peace")
                .publishedYear(2002L).build();
        logger.info("Preloading "+ bookRepository.save(bookPeace));

    }

}
