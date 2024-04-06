package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.filmorate.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import java.util.List;
import ru.yandex.practicum.filmorate.util.LogThis;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/genres")
public class GenreController {
    private final GenreService genreService;

    @GetMapping
    @LogThis
    public List<Genre> getGenres() {
        return genreService.getGenres();
    }

    @GetMapping(value = "/{id}")
    @LogThis
    public Genre getGenre(@PathVariable(required = true) Long id) {
        return genreService.getGenre(id);
    }
}