package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public void addLike(Long filmId, Long userId) {
        Film film = filmStorage.getFilm(filmId);
        if (film == null) {
            throw new FilmNotFoundException("film not found");
        }
        film.getLikes().add(userId);
    }

    public void removeLike(Long filmId, Long userId) {
        Film film = filmStorage.getFilm(filmId);
        if (film == null) {
            throw new FilmNotFoundException("film not found");
        }
        userStorage.getUser(userId);
        film.getLikes().remove(userId);
    }

    public Set<Film> getTopFilms(Integer count) {
        return filmStorage.getFilms().stream().sorted(new Comparator<Film>() {
            @Override
            public int compare(Film o1, Film o2) {
                return o2.getLikesCount() - o1.getLikesCount();
            }
        }).limit(count).collect(Collectors.toSet());
    }
}