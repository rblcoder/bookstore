package com.company.bookStore.integration;

import com.company.bookStore.model.Book;
import com.company.bookStore.model.Genre;
import com.company.bookStore.repository.BookRepository;
import com.company.bookStore.repository.GenreRepository;
import com.company.bookStore.service.BookDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

    BookDto bookDtoPeace;

    BookDto bookDtoJavaLearn;

    List<BookDto> bookDtoList;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    public void setup() {


        Genre genreNonFiction = new Genre(1L, "Non fiction");

        logger.info("Preloading " + genreRepository.save(genreNonFiction));

        Genre genreNonFictionSelfHelp = new Genre(2L, "Self-Help");
        genreNonFictionSelfHelp.setParent(genreNonFiction);

        logger.info("Preloading " + genreRepository.save(genreNonFictionSelfHelp));


        Book bookPeace = Book.builder()
                .id(1L).title("Peace")
                .publishedYear(2002L)
                .genre(genreNonFictionSelfHelp).build();
        logger.info("Preloading " + bookRepository.save(bookPeace));

        Genre genreComputer = new Genre(3L, "Computer Technology");
        genreComputer.setParent(genreNonFiction);

        logger.info("Preloading " + genreRepository.save(genreComputer));

        Genre genreJava = new Genre(4L, "Java");
        genreJava.setParent(genreComputer);

        logger.info("Preloading " + genreRepository.save(genreJava));


        Book bookJavaLearn = Book.builder()
                .id(2L).title("Learn Java 8")
                .publishedYear(2020L)
                .genre(genreJava).build();

        logger.info("Preloading " + bookRepository.save(bookJavaLearn));

        bookDtoPeace = BookDto.builder()
                .id(bookPeace.getId()).title(bookPeace.getTitle())
                .publishedYear(bookPeace.getPublishedYear())
                .genre(bookPeace.getGenre()).build();

        bookDtoJavaLearn = BookDto.builder()
                .id(bookJavaLearn.getId()).title(bookJavaLearn.getTitle())
                .publishedYear(bookJavaLearn.getPublishedYear())
                .genre(bookJavaLearn.getGenre()).build();

        bookDtoList = new ArrayList<>();
        bookDtoList.add(bookDtoPeace);
        bookDtoList.add(bookDtoJavaLearn);


    }

    @Test
    public void testGetBooks() throws Exception {

        mockMvc.perform(get("/api/v1/books"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(objectMapper.writeValueAsString(bookDtoList)))
                .andDo(print());

    }

    @Test
    public void testGetBookById() throws Exception {

        mockMvc.perform(get("/api/v1/books/1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(objectMapper.writeValueAsString(bookDtoPeace)))
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
                .andExpect(content().json(objectMapper.writeValueAsString(bookDtoPeace)))
                .andDo(print());

    }

}
