package ru.yandex.practicum.filmorate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import javax.validation.Valid;
import ru.yandex.practicum.filmorate.error.EntityAlreadyExistException;
import ru.yandex.practicum.filmorate.model.Film;

@RestController
@Slf4j
public class FilmController {
    private Map<Long, Film> mapOfFilms = new HashMap<>();
    private Long id = 0L;

    @GetMapping("/films")
    public List<Film> getFilms() {
        log.info("get all films");
        return new ArrayList<>(mapOfFilms.values());
    }

    @PostMapping("/films")
    public Film addFilm(@Valid @RequestBody Film film) throws EntityAlreadyExistException {
        log.info("add new film");
        if ((film.getId() != null) && (mapOfFilms.get(film.getId()) != null)) {
            throw new EntityAlreadyExistException("film already exists");//TODO
        }
        film.setId(++id);
        mapOfFilms.put(film.getId(), film);
        return film;
    }

    @PutMapping("/films/{filmId}")
    public Film updateFilm(@PathVariable Long filmId, @Valid @RequestBody Film film) {
        log.info("update film id: " + filmId);
        if (filmId == null || (mapOfFilms.get(filmId) == null)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "film not found"
            );
        }
        film.setId(filmId);
        mapOfFilms.put(film.getId(), film);
        return film;
    }
}