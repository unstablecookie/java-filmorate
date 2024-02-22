package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import ru.yandex.practicum.filmorate.exception.EntityAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private Map<Long, Film> mapOfFilms = new HashMap<>();
    private Long id = 0L;

    public List<Film> getFilms() {
        return new ArrayList<>(mapOfFilms.values());
    }

    public Film getFilm(Long filmId) {
        Film film = mapOfFilms.get(filmId);
        if (film == null) {
            throw new EntityNotFoundException("film not found");
        }
        return film;
    }

    public Film addFilm(Film film) throws EntityAlreadyExistException {
        if ((film.getId() != null) && (mapOfFilms.get(film.getId()) != null)) {
            throw new EntityAlreadyExistException("film id:" + film.getId() + "already exists");
        }
        film.setId(++id);
        mapOfFilms.put(film.getId(), film);
        return film;
    }

    public Film updateFilm(Long filmId, Film film) {
        if (filmId == null) {
            filmId = film.getId();
        }
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