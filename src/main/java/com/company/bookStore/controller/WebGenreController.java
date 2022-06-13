package com.company.bookStore.controller;

import com.company.bookStore.model.Genre;
import com.company.bookStore.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
