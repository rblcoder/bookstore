package com.company.bookStore.service;

import com.company.bookStore.model.Genre;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BookDtoTest {
    @Test
    void shouldBeEqual() {
        Genre genreClassics = new Genre(2L, "Classics");
        BookDto bookDtoIndependence = BookDto.builder()
                .id(2L).title("India Independence")
                .publishedYear(1998L)
                .genre(genreClassics).build();

        BookDto bookDtoIndependenceAnother =
                BookDto.builder()
                        .id(2L).title("India Independence").publishedYear(1998L)
                        .genre(genreClassics).build();

        Assertions.assertEquals(bookDtoIndependence, bookDtoIndependenceAnother);
    }

    @Test
    void shouldConvertToString() {
        Genre genreClassics = new Genre(2L, "Classics");
        Assertions.assertEquals(
                "BookDto.BookDtoBuilder(id=2, title=India Independence, publishedYear=1998," +
                        " genre=Genre{id=2, name='Classics'})",
                BookDto.builder().id(2L)
                        .title("India Independence")
                        .publishedYear(1998L)
                        .genre(genreClassics)
                        .toString());
    }

}
