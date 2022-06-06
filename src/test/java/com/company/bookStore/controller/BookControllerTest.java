package com.company.bookStore.controller;

import com.company.bookStore.service.BookDto;
import com.company.bookStore.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class BookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    @Test
    void shouldGetAllBooks() throws Exception {
        List<BookDto> bookDtoList = new ArrayList<>();
        BookDto bookDtoPeace = new BookDto(1L, "Peace", 2002L);
        BookDto bookDtoIndependence = BookDto.builder()
                .id(2L)
                .title("India Independence")
                .publishedYear(1998L).build();
        bookDtoList.add(bookDtoPeace);
        bookDtoList.add(bookDtoIndependence);

        when(bookService.getAllBooks()).thenReturn(bookDtoList);

        mockMvc.perform(get("/api/v1/books"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(bookDtoList)));
    }

    @Test
    void shouldSaveNewBook() throws Exception {
        BookDto bookDtoPeace = BookDto.builder()
                .title("Peace")
                .publishedYear(2002L).build();

        BookDto bookDtoPeaceSaved = BookDto.builder()
                .id(1L)
                .title("Peace")
                .publishedYear(2002L).build();

        when(bookService.saveBook(bookDtoPeace)).thenReturn(bookDtoPeaceSaved);

        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(bookDtoPeace)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(bookDtoPeaceSaved)));
    }
}