package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import javax.validation.Valid;
import ru.yandex.practicum.filmorate.exception.EntityAlreadyExistException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.util.LogThis;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/films")
public class FilmController {
    private final FilmService filmService;

    @GetMapping
    @LogThis
    public List<Film> getFilms() {
        return filmService.getFilms();
    }

    @GetMapping(value = "/{id}")
    @LogThis
    public Film getFilm(@PathVariable(required = true) Long id) {
        return filmService.getFilm(id);
    }

    @PostMapping
    @LogThis
    public Film addFilm(@Valid @RequestBody Film film) throws EntityAlreadyExistException {
        return filmService.addFilm(film);
    }

    @PutMapping(value = {"","/{filmId}"})
    @LogThis
    public Film updateFilm(@PathVariable(required = false) Long filmId, @Valid @RequestBody Film film) {
        return filmService.updateFilm(filmId, film);
    }

    @PutMapping(value = "/{id}/like/{userId}")
    @LogThis
    public void addLike(@PathVariable Long id,
                        @PathVariable Long userId) {
        filmService.addLike(id, userId);
    }

    @DeleteMapping(value = "/{id}/like/{userId}")
    @LogThis
    public void removeLike(@PathVariable Long id,
                           @PathVariable Long userId) {
        filmService.removeLike(id, userId);
    }

    @GetMapping(value = "/popular")
    @LogThis
    public List<Film> getTopFilms(@RequestParam(defaultValue = "10", required = false) Integer count) {
        return filmService.getTopFilms(count);
    }
}