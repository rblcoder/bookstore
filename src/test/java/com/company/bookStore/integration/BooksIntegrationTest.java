package com.company.bookStore.integration;

import com.company.bookStore.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BooksIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    BookRepository bookRepository;

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
