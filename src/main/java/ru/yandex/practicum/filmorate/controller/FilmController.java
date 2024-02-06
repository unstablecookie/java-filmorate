package ru.yandex.practicum.filmorate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.validation.Valid;

import ru.yandex.practicum.filmorate.errors.InvalidFilmDataException;
import ru.yandex.practicum.filmorate.errors.FilmAlreadyExistException;
import ru.yandex.practicum.filmorate.model.Film;




@RestController
@Slf4j
public class FilmController {
    
    static final LocalDate INITIAL_DATE = LocalDate.of(1895, 12, 28);
    private Map<Long, Film> mapOfFilms = new HashMap<>();
    
    private Long id = 0l;

    @GetMapping("/films")
    public List<Film> getFilms() {
        log.info("get all films");
        return List.of(mapOfFilms.values().toArray(new Film[mapOfFilms.values().size()]));
    }

    @PostMapping("/films")
    public Film addFilm(@Valid  @RequestBody Film film) throws FilmAlreadyExistException, InvalidFilmDataException {
        log.info("add new film");
        filmValidation(film);
        if (mapOfFilms.get(film.getId()) != null) {
            throw new FilmAlreadyExistException("film already exists");//TODO
        }
        film.setId(++id);
        mapOfFilms.put(film.getId(), film);
        return film;
    }

    @PutMapping("/films")
    public Film updateFilm(@Valid @RequestBody Film film) throws InvalidFilmDataException {
        log.info("update film");
        if (film.getId() == null || (mapOfFilms.get(film.getId()) == null)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "film not found"
            );
        }
        filmValidation(film);
        mapOfFilms.put(film.getId(), film);
        return film;
    }

    public void filmValidation(Film film) throws InvalidFilmDataException {
        if ((film.getDuration() <= 0) || film.getReleaseDate().isBefore(INITIAL_DATE)) {
            log.debug("film.getReleaseDate().isBefore(INITIAL_DATE)) : " + film.getReleaseDate().isBefore(INITIAL_DATE));
            throw new InvalidFilmDataException("wrong film data");
        }
    }
}
