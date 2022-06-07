package com.company.bookStore.service;

import com.company.bookStore.model.Genre;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Schema(description = "Details of book")
//@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
public class BookDto implements Serializable {

    private final Long id;
    private final String title;
    private final Long publishedYear;
    private final Genre genre;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDto bookDto = (BookDto) o;
        return Objects.equals(id, bookDto.id)
                && Objects.equals(title, bookDto.title)
                && Objects.equals(publishedYear, bookDto.publishedYear)
                && Objects.equals(genre, bookDto.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, publishedYear, genre);
    }
}
