package com.company.bookStore.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

}