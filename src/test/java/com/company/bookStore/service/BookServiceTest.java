package com.company.bookStore.service;

import com.company.bookStore.exception.BookNotFoundException;
import com.company.bookStore.model.Book;
import com.company.bookStore.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    private final ModelMapper modelMapper = new ModelMapper();
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookService bookService;

    @Test
    void shouldGetAllBooks() {
        List<BookDto> bookDtoList = new ArrayList<>();
        BookDto bookDtoPeace = new BookDto(1L, "Peace", 2002L);
        BookDto bookDtoIndependence = new BookDto(2L, "India Independence", 1998L);
        bookDtoList.add(bookDtoPeace);
        bookDtoList.add(bookDtoIndependence);

        List<Book> bookList = new ArrayList<>();
        Book bookPeace = modelMapper.map(bookDtoPeace, Book.class);
        Book bookIndependence = modelMapper.map(bookDtoIndependence, Book.class);
        bookList.add(bookPeace);
        bookList.add(bookIndependence);

        when(bookRepository.findAll()).thenReturn(bookList);

        Assertions.assertEquals(bookDtoList, bookService.getAllBooks());
    }

    @Test
    void shouldGetBookById(){
        BookDto bookDtoPeace = BookDto
                .builder().id(1L)
                .title("Peace").publishedYear(2002L).build();

        Book bookPeace = modelMapper.map(bookDtoPeace, Book.class);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(bookPeace));

        Assertions.assertEquals(bookDtoPeace, bookService.getBookById(bookDtoPeace.getId()));


    }
}
