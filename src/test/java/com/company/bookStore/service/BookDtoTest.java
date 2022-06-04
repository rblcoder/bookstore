package com.company.bookStore.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BookDtoTest {
    @Test
    void shouldBeEqual() {
        BookDto bookDtoIndependence = new BookDto(2L, "India Independence", 1998L);
        BookDto bookDtoIndependenceAnother =
                new BookDto(2L, "India Independence", 1998L);
        Assertions.assertEquals(bookDtoIndependence, bookDtoIndependenceAnother);
    }

    @Test
    void shouldConvertToString(){
        Assertions.assertEquals("BookDto.BookDtoBuilder(id=2, title=India Independence, publishedYear=1998)",
                BookDto.builder().id(2L)
                .title("India Independence")
                .publishedYear(1998L)
                .toString());
    }

}
