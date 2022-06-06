package com.company.bookStore.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "book",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"title", "publishedYear"})})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty(message = "Book title cannot be empty")
    @NonNull
    @Column
    private String title;

    @NonNull
    @Column
    private Long publishedYear;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id)
                && Objects.equals(title, book.title)
                && Objects.equals(publishedYear,
                book.publishedYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, publishedYear);
    }
}