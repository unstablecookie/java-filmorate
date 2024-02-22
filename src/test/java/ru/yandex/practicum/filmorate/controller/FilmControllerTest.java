package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.Duration;
import java.util.List;
import java.time.LocalDate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.exception.EntityAlreadyExistException;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

@SpringBootTest
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class FilmControllerTest {
    @Autowired
    private FilmController filmController;

    @Autowired
    private FilmStorage filmStorage;

    @Autowired
    private FilmService filmService;

    @Test
    void filmContextLoads_success() {
        assertNotNull(filmController);
    }

    @Test
    void addFilm_success() throws EntityAlreadyExistException {
        //given
        Long id = 1L;
        String name = "Wars";
        String description = "wars are everywhere";
        LocalDate releaseDate = LocalDate.of(2000,1,1);
        Duration duration = Duration.ofMinutes(100);
        Film film = new Film();
        film.setName(name);
        film.setDescription(description);
        film.setReleaseDate(releaseDate);
        film.setDuration(duration.toMinutes());
        //when
        filmController.addFilm(film);
        List<Film> films = filmController.getFilms();
        Film firstFilm = films.get(0);
        //then
        assertNotNull(firstFilm);
        assertEquals(id,firstFilm.getId());
        assertEquals(name,firstFilm.getName());
        assertEquals(description,firstFilm.getDescription());
        assertEquals(releaseDate,firstFilm.getReleaseDate());
        assertEquals(duration.toMinutes(),firstFilm.getDuration());
    }

    @Test
    void addFilm_failure_EntityAlreadyExistException() throws EntityAlreadyExistException {
        //given
        String name = "Wars";
        String description = "wars are everywhere";
        LocalDate releaseDate = LocalDate.of(2000,1,1);
        Duration duration = Duration.ofMinutes(100);
        Film film = new Film();
        film.setName(name);
        film.setDescription(description);
        film.setReleaseDate(releaseDate);
        film.setDuration(duration.toMinutes());
        filmController.addFilm(film);
        //then
        assertThrows(EntityAlreadyExistException.class, () -> {
            filmController.addFilm(film); });
    }

    @Test
    void addFilm_success_withEmptyDescription() throws EntityAlreadyExistException {
        //given
        Long id = 1L;
        String name = "Wars";
        String description = "";
        LocalDate releaseDate = LocalDate.of(2000,1,1);
        Duration duration = Duration.ofMinutes(100);
        Film film = new Film();
        film.setName(name);
        film.setDescription(description);
        film.setReleaseDate(releaseDate);
        film.setDuration(duration.toMinutes());
        //when
        filmController.addFilm(film);
        List<Film> films = filmController.getFilms();
        Film firstFilm = films.get(0);
        //then
        assertNotNull(firstFilm);
        assertEquals(id,firstFilm.getId());
        assertEquals(name,firstFilm.getName());
        assertEquals(description,firstFilm.getDescription());
        assertEquals(releaseDate,firstFilm.getReleaseDate());
        assertEquals(duration.toMinutes(),firstFilm.getDuration());
    }

    @Test
    void addFilm_success_withNullDescription() throws EntityAlreadyExistException {
        //given
        Long id = 1L;
        String name = "Wars";
        String description = "";
        LocalDate releaseDate = LocalDate.of(2000,1,1);
        Duration duration = Duration.ofMinutes(100);
        Film film = new Film();
        film.setName(name);
        film.setDescription(description);
        film.setReleaseDate(releaseDate);
        film.setDuration(duration.toMinutes());
        //when
        filmController.addFilm(film);
        List<Film> films = filmController.getFilms();
        Film firstFilm = films.get(0);
        //then
        assertNotNull(firstFilm);
        assertEquals(id,firstFilm.getId());
        assertEquals(name,firstFilm.getName());
        assertEquals(description,firstFilm.getDescription());
        assertEquals(releaseDate,firstFilm.getReleaseDate());
        assertEquals(duration.toMinutes(),firstFilm.getDuration());
    }

    @Test
    void updateFilm_success() throws EntityAlreadyExistException {
        //given
        Long id = 1L;
        String name = "Wars";
        String description = "wars are everywhere";
        LocalDate releaseDate = LocalDate.of(2000,1,1);
        Duration duration = Duration.ofMinutes(100);
        Film film = new Film();
        film.setName(name);
        film.setDescription(description);
        film.setReleaseDate(releaseDate);
        film.setDuration(duration.toMinutes());
        filmController.addFilm(film);
        film.setId(id);
        String newName = "newWars";
        film.setName(newName);
        //when
        filmController.updateFilm(id, film);
        List<Film> films = filmController.getFilms();
        Film firstFilm = films.get(0);
        //then
        assertNotNull(firstFilm);
        assertEquals(id,firstFilm.getId());
        assertEquals(newName,firstFilm.getName());
        assertEquals(description,firstFilm.getDescription());
        assertEquals(releaseDate,firstFilm.getReleaseDate());
        assertEquals(duration.toMinutes(),firstFilm.getDuration());
    }

    @Test
    void updateFilm_failure_withNullId() throws EntityAlreadyExistException {
        //given
        String name = "Wars";
        String description = "wars are everywhere";
        LocalDate releaseDate = LocalDate.of(2000,1,1);
        Duration duration = Duration.ofMinutes(100);
        Film film = new Film();
        film.setName(name);
        film.setDescription(description);
        film.setReleaseDate(releaseDate);
        film.setDuration(duration.toMinutes());
        filmController.addFilm(film);
        String newName = "newWars";
        film.setName(newName);
        //when
        film.setId(null);
        Long nullId = null;
        //then
        assertThrows(ResponseStatusException.class, () -> {
            filmController.updateFilm(nullId, film);
        });
    }

    @Test
    void updateFilm_failure_withWrongId() throws EntityAlreadyExistException {
        //given
        Long id = 1L;
        String name = "Wars";
        String description = "wars are everywhere";
        LocalDate releaseDate = LocalDate.of(2000,1,1);
        Duration duration = Duration.ofMinutes(100);
        Film film = new Film();
        film.setName(name);
        film.setDescription(description);
        film.setReleaseDate(releaseDate);
        film.setDuration(duration.toMinutes());
        filmController.addFilm(film);
        film.setId(id);
        String newName = "newWars";
        film.setName(newName);
        //when
        Long wrongId = -999L;
        //then
        assertThrows(ResponseStatusException.class, () -> {
            filmController.updateFilm(wrongId, film);
        });
    }

    @Test
    void getFilms_success() throws EntityAlreadyExistException {
        //given
        String name = "Wars";
        String description = "wars are everywhere";
        LocalDate releaseDate = LocalDate.of(2000,1,1);
        Duration duration = Duration.ofMinutes(100);
        Film film = new Film();
        film.setName(name);
        film.setDescription(description);
        film.setReleaseDate(releaseDate);
        film.setDuration(duration.toMinutes());
        filmController.addFilm(film);
        String secondName = "Wars2";
        film.setName(secondName);
        film.setId(null);
        filmController.addFilm(film);
        //when
        List<Film> films = filmController.getFilms();
        //then
        assertEquals(2, films.size());
    }

    @Test
    void getFilms_success_emptyCollection() {
        //when
        List<Film> films = filmController.getFilms();
        //then
        assertEquals(0, films.size());
    }
}