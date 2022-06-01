package com.company.bookStore.service;

import com.company.bookStore.exception.BookNotFoundException;
import com.company.bookStore.model.Book;
import com.company.bookStore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::convertEntityToDto).collect(Collectors.toList());
    }

    public BookDto getBookById(Long id) {
        return convertEntityToDto(bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException()));
    }

    private BookDto convertEntityToDto(Book book) {
        BookDto bookDto = new BookDto(book.getId(), book.getTitle(), book.getPublishedYear());
        return bookDto;
    }
}
