package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import ru.yandex.practicum.filmorate.exception.EntityAlreadyExistException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

@RestController
@Slf4j
public class FilmController {

    private final FilmStorage filmStorage;

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmStorage filmStorage, FilmService filmService) {
        this.filmStorage = filmStorage;
        this.filmService = filmService;
    }

    @GetMapping(value = "/films")
    public List<Film> getFilms() {
        log.info("get all films");
        return filmStorage.getFilms();
    }

    @GetMapping(value = "/films/{id}")
    public Film getFilm(@PathVariable(required = true) Long id) {
        log.info("get film id :" + id);
        return filmStorage.getFilm(id);
    }

    @PostMapping(value = "/films")
    public Film addFilm(@Valid @RequestBody Film film) throws EntityAlreadyExistException {
        log.info("add new film:" + film.getName());
        return filmStorage.addFilm(film);
    }

    @PutMapping(value = {"/films","/films/{filmId}"})
    public Film updateFilm(@PathVariable(required = false) Long filmId, @Valid @RequestBody Film film) {
        return filmStorage.updateFilm(filmId, film);
    }

    @PutMapping(value = "/films/{id}/like/{userId}")
    public void addLike(@PathVariable(required = true) Long id,
                        @PathVariable(required = true) Long userId) {
        log.info("add like to movie id:" + id + " from user id:" + userId);
        filmService.addLike(id, userId);
    }

    @DeleteMapping(value = "/films/{id}/like/{userId}")
    public void removeLike(@PathVariable(required = true) Long id,
                           @PathVariable(required = true) Long userId) {
        log.info("remove like from movie id:" + id + " from user id:" + userId);
        filmService.removeLike(id, userId);
    }

    @GetMapping(value = "/films/popular")
    public Set<Film> getTopFilms(@RequestParam(defaultValue = "10", required = false) Integer count) {
        log.info("get top " + count + " movies");
        return filmService.getTopFilms(count);
    }
}