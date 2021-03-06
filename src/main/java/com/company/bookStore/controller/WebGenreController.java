package com.company.bookStore.controller;

import com.company.bookStore.exception.GenreNotFoundException;
import com.company.bookStore.model.Genre;
import com.company.bookStore.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class WebGenreController {
    @Autowired
    private GenreService genreService;

    @GetMapping("/genres")
    public String listAllGenres(Model model) {
        List<Genre> genreList = genreService.getAllGenres();
        model.addAttribute("genres", genreList);

        return "genre/genres";
    }

    @GetMapping("/genres/new")
    public String newGenre(Model model) {
        model.addAttribute("genre", new Genre());
        model.addAttribute("pageTitle", "Create New Genre");
        model.addAttribute("allGenres", genreService.getAllGenres());
        return "genre/genre_form";
    }

    @GetMapping("/genres/edit/{id}")
    public String editGenre(Model model, @PathVariable Long id) {
        Genre genre = genreService.getGenreById(id).orElseThrow(() -> new GenreNotFoundException());
        model.addAttribute("genre", genre);
        model.addAttribute("pageTitle", "Edit Genre");
        model.addAttribute("allGenres", genreService.getAllGenres());
        return "genre/genre_form";
    }


    @PostMapping("/genres/save")
    public String saveGenre(@Valid Genre genre, BindingResult result, Model model) {
        if (result.hasErrors()) {
            if (genre.getId() != null) {
                model.addAttribute("pageTitle", "Edit Genre");
            } else {
                model.addAttribute("pageTitle", "Create New Genre");
            }
            model.addAttribute("allGenres", genreService.getAllGenres());
            return "genre/genre_form";
        }
        genreService.save(genre);
        return "redirect:/genres";
    }
}
