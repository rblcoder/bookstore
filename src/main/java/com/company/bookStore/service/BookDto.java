package com.company.bookStore.service;

import lombok.Data;

import java.io.Serializable;

@Data
public class BookDto implements Serializable {
    private final Long id;
    private final String title;
    private final Long publishedYear;
}
