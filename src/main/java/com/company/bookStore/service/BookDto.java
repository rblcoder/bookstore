package com.company.bookStore.service;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Schema(description = "Details of book")
@Data
public class BookDto implements Serializable {
    private final Long id;
    private final String title;
    private final Long publishedYear;
}
