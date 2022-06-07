package com.company.bookStore.integration;

import com.company.bookStore.model.Book;
import com.company.bookStore.model.Genre;
import com.company.bookStore.repository.BookRepository;
import com.company.bookStore.repository.GenreRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BooksIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    GenreRepository genreRepository;

    Logger logger = LoggerFactory.getLogger(BooksIntegrationTest.class);

    @BeforeAll
    public void setup() {
        Genre genreNonFiction = new Genre(1L, "Non fiction");
        logger.info("Preloading " + genreRepository.save(genreNonFiction));

        Book bookPeace = Book.builder()
                .id(1L).title("Peace")
                .publishedYear(2002L)
                .genre(genreNonFiction).build();
        logger.info("Preloading " + bookRepository.save(bookPeace));
    }

    @Test
    public void testGetBooks() throws Exception {

        mockMvc.perform(get("/api/v1/books"))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());

    }

    @Test
    public void testGetBookById() throws Exception {

        mockMvc.perform(get("/api/v1/books/1"))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());

    }

    @Test
    public void testGetBooksByTitle() throws Exception {

        mockMvc.perform(get("/api/v1/books/title/peace"))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());

    }

    @Test
    public void testGetBookByTitleAndPublishedYear() throws Exception {

        mockMvc.perform(get("/api/v1/books/title/Peace/publishedyear/2002"))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());

    }

}
