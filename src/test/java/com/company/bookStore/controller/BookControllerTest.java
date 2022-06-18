package com.company.bookStore.controller;

import com.company.bookStore.model.Genre;
import com.company.bookStore.service.BookDto;
import com.company.bookStore.service.BookService;
import com.company.bookStore.service.GenreService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookControllerTest {

    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    @MockBean
    private GenreService genreService;

    private Genre genreNonFiction;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeAll
    void setUp() {
        Genre genreNonFiction = new Genre(1L, "Non Fiction");
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @WithMockUser
    void shouldGetAllBooks() throws Exception {
        List<BookDto> bookDtoList = new ArrayList<>();
        BookDto bookDtoPeace = BookDto.builder().id(1L)
                .title("Peace").publishedYear(2002L).genre(genreNonFiction).build();

        BookDto bookDtoIndependence = BookDto.builder()
                .id(2L)
                .title("India Independence")
                .publishedYear(1998L).genre(genreNonFiction).build();
        bookDtoList.add(bookDtoPeace);
        bookDtoList.add(bookDtoIndependence);

        when(bookService.getAllBooks()).thenReturn(bookDtoList);

        mockMvc.perform(get("/api/v1/books"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(bookDtoList)));
    }

    @Test
    @WithMockUser
    void shouldGetBookById() throws Exception {
        BookDto bookDtoPeace = BookDto.builder()
                .id(1L).title("Peace")
                .publishedYear(2002L).genre(genreNonFiction).build();

        when(bookService.getBookById(1L)).thenReturn(bookDtoPeace);

        mockMvc.perform(get("/api/v1/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(bookDtoPeace)));
    }

    @Test
    @WithMockUser
    void shouldFindBooksByTitleIgnoringCase() throws Exception {
        List<BookDto> bookDtoList = new ArrayList<>();
        BookDto bookDtoPeace = BookDto.builder()
                .id(1L).title("Peace")
                .publishedYear(2002L).genre(genreNonFiction).build();
        bookDtoList.add(bookDtoPeace);

        when(bookService.getBooksByTitle("peace")).thenReturn(bookDtoList);

        mockMvc.perform(get("/api/v1/books/title/peace"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(bookDtoList)));
    }

    @Test
    @WithMockUser
    void shouldSaveNewBook() throws Exception {
        BookDto bookDtoPeace = BookDto.builder()
                .title("Peace")
                .publishedYear(2002L).genre(genreNonFiction).build();

        BookDto bookDtoPeaceSaved = BookDto.builder()
                .id(1L)
                .title("Peace")
                .publishedYear(2002L).genre(genreNonFiction).build();

        when(bookService.saveBook(bookDtoPeace)).thenReturn(bookDtoPeaceSaved);

        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(bookDtoPeace)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(bookDtoPeaceSaved)));
    }
}
