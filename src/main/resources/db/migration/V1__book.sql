CREATE TABLE book (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   title VARCHAR(255) NOT NULL,
   published_year BIGINT NOT NULL,
   CONSTRAINT pk_book PRIMARY KEY (id)
);

ALTER TABLE book ADD CONSTRAINT uc_book_title_published_year UNIQUE (title, published_year);