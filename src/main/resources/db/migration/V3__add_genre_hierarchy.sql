ALTER TABLE genre ALTER COLUMN name SET NOT NULL;

ALTER TABLE genre ADD COLUMN parent_id BIGINT;

ALTER TABLE genre ADD CONSTRAINT uc_genre_name UNIQUE (name);

ALTER TABLE genre ADD CONSTRAINT FK_GENRE_ON_PARENT FOREIGN KEY (parent_id) REFERENCES genre (id);