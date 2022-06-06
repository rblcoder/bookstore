package com.company.bookStore.controller;

import com.company.bookStore.service.BookDto;
import com.company.bookStore.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Operation(summary = "Get all books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all the books",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookDto[].class))})})
    @GetMapping
    public ResponseEntity<List<BookDto>> getBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @Operation(summary = "Get a book by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the book",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content)})
    @GetMapping("{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @Operation(summary = "Get book by Title")
    @GetMapping("/title/{title}")
    public ResponseEntity<List<BookDto>> getBooksByTitle(@PathVariable String title) {
        return ResponseEntity.ok(bookService.getBooksByTitle(title));
    }

    @Operation(summary = "Get book by Title and Published Year")
    @GetMapping("/title/{title}/publishedyear/{publishedYear}")
    public ResponseEntity<BookDto> getBooksByTitle(@PathVariable String title, @PathVariable Long publishedYear) {
        return ResponseEntity.ok(bookService.getBookByTitlePublishedYear(title, publishedYear));
    }

    @Operation(summary = "Add a new book",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Book to add",
                    required = true,
                    content = @Content(schema = @Schema(implementation = BookDto.class))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book added successfully ",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid json supplied",
                    content = @Content)})
    @PostMapping
    public ResponseEntity<BookDto> newBook(@org.springframework.web.bind.annotation.RequestBody  BookDto bookDto) {
        return ResponseEntity.ok(bookService.saveBook(bookDto));
    }

}
