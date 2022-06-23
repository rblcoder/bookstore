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

        return convertEntityToDto(bookRepository
                .findById(id).orElseThrow(() -> new BookNotFoundException()));
    }

    public List<BookDto> getBooksByTitle(String title) {
        return bookRepository.findBooksByTitleIgnoreCase(title)
                .stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    public BookDto getBookByTitlePublishedYear(String title, Long publishedYear) {
        return convertEntityToDto(bookRepository
                .findBookByTitleAndPublishedYear(title, publishedYear)
                .orElseThrow(() -> new BookNotFoundException()));
    }

    private BookDto convertEntityToDto(Book book) {
        BookDto bookDto = BookDto.builder().id(book.getId())
                .title(book.getTitle())
                .publishedYear(book.getPublishedYear())
                .genre(book.getGenre()).build();

        return bookDto;
    }

    private Book convertDtoToEntity(BookDto bookDto) {
        Book book = Book.builder()
                .id(bookDto.getId())
                .title(bookDto.getTitle())
                .publishedYear(bookDto.getPublishedYear())
                .genre(bookDto.getGenre()).build();
        return book;
    }

    public BookDto saveBook(BookDto bookDto) {
        return convertEntityToDto(bookRepository.save(convertDtoToEntity(bookDto)));
    }

    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }
}
