package com.company.bookStore.service;

import com.company.bookStore.model.Genre;
import com.company.bookStore.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Genre save(Genre genre) {
        return genreRepository.save(genre);
    }

    public Optional<Genre> getGenreById(Long id) {
        return genreRepository.findById(id);
    }
}
