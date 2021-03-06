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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public BookDto convertBookToBookDto(Book book){
        BookDto bookDto = BookDto.builder()
                .id(book.getId()).title(book.getTitle())
                .publishedYear(book.getPublishedYear())
                .genre(book.getGenre()).build();
        return bookDto;
    }

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

        bookDtoPeace = convertBookToBookDto(bookPeace);

        bookDtoJavaLearn = convertBookToBookDto(bookJavaLearn);

        Book bookJavaGenericsCollections = Book.builder()
                .id(3L).title("Learn Java Generics and Collections")
                .publishedYear(2021L)
                .genre(genreJava).build();

        logger.info("Preloading " + bookRepository.save(bookJavaGenericsCollections));

        Book bookImplementDesignPatternsUsingJava = Book.builder()
                .id(4L).title("Implement Design Patterns Using Java")
                .publishedYear(2019L)
                .genre(genreJava).build();

        logger.info("Preloading " + bookRepository.save(bookImplementDesignPatternsUsingJava));

        BookDto bookDtoJavaGenericsCollections =
                convertBookToBookDto(bookJavaGenericsCollections);

        BookDto bookDtoImplementDesignPatternsUsingJava =
                convertBookToBookDto(bookImplementDesignPatternsUsingJava);

        bookDtoList = new ArrayList<>();
        bookDtoList.add(bookDtoPeace);
        bookDtoList.add(bookDtoJavaLearn);
        bookDtoList.add(bookDtoJavaGenericsCollections);
        bookDtoList.add(bookDtoImplementDesignPatternsUsingJava);


    }

    @Test
    @WithMockUser
    public void testGetBooks() throws Exception {

        mockMvc.perform(get("/api/v1/books/all"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(objectMapper.writeValueAsString(bookDtoList)))
                .andDo(print());

    }

    @Test
    @WithMockUser
    public void testGetBooksPage0Size2() throws Exception {

        mockMvc.perform(get("/api/v1/books?page=0&size=2"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(objectMapper
                        .writeValueAsString(bookDtoList.stream().limit(2)
                                .collect(Collectors.toList()))))
                .andDo(print());

    }

    @Test
    @WithMockUser
    public void testGetBookById() throws Exception {

        mockMvc.perform(get("/api/v1/books/1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(objectMapper.writeValueAsString(bookDtoPeace)))
                .andDo(print());

    }

    @Test
    @WithMockUser
    public void testGetBooksByTitle() throws Exception {

        mockMvc.perform(get("/api/v1/books/title/peace"))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());

    }

    @Test
    @WithMockUser
    public void testGetBookByTitleAndPublishedYear() throws Exception {

        mockMvc.perform(get("/api/v1/books/title/Peace/publishedyear/2002"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(objectMapper.writeValueAsString(bookDtoPeace)))
                .andDo(print());

    }

}
