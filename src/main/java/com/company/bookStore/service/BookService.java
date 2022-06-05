package com.company.bookStore.service;

import com.company.bookStore.exception.BookNotFoundException;
import com.company.bookStore.model.Book;
import com.company.bookStore.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::convertEntityToDto).collect(Collectors.toList());
    }

    public BookDto getBookById(Long id) {

        return convertEntityToDto(bookRepository
                .findById(id).orElseThrow(() -> new BookNotFoundException()));
    }

    public List<BookDto> getBooksByTitle(String title) {
        return bookRepository.findBooksByTitleIgnoreCase(title)
                .stream().map(b ->
                        modelMapper.map(b, BookDto.BookDtoBuilder.class).build()).collect(Collectors.toList());
    }

    public BookDto getBookByTitlePublishedYear(String title, Long publishedYear) {
        return convertEntityToDto(bookRepository
                .findBookByTitleAndPublishedYear(title, publishedYear)
                .orElseThrow(() -> new BookNotFoundException()));
    }

    private BookDto convertEntityToDto(Book book) {
        BookDto bookDto = new BookDto(book.getId(), book.getTitle(), book.getPublishedYear());
        return bookDto;
    }
}
