package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.Duration;
import java.util.List;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;

import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.error.*;

@SpringBootTest
public class FilmControllerTest {
    @Autowired
    private Validator validator;
    @Autowired
    private FilmController filmController;

    @BeforeEach
    void init() {
        filmController = new FilmController();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void filmContextLoads_success() {
        assertNotNull(filmController);
    }

    @Test
    void addFilm_success() throws InvalidFilmDataException, FilmAlreadyExistException {
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
    void addFilm_failure_filmAlreadyExistException() throws InvalidFilmDataException, FilmAlreadyExistException {
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
        assertThrows(FilmAlreadyExistException.class, () -> {
            filmController.addFilm(film); });
    }

    @Test
    void addFilm_failure_invalidFilmDataExceptionWithNegativeDuration() throws InvalidFilmDataException,
            FilmAlreadyExistException {
        //given
        Long id = 1L;
        String name = "Wars";
        String description = "wars are everywhere";
        LocalDate releaseDate = LocalDate.of(2000,1,1);
        Film film = new Film();
        film.setName(name);
        film.setDescription(description);
        film.setReleaseDate(releaseDate);
        //when
        Duration duration = Duration.ofMinutes(-100);
        film.setDuration(duration.toMinutes());
        //then
        assertThrows(InvalidFilmDataException.class, () -> {
            filmController.addFilm(film); });
    }

    @Test
    void addFilm_failure_invalidFilmDataExceptionWithZeroDuration() throws InvalidFilmDataException,
            FilmAlreadyExistException {
        //given
        Long id = 1L;
        String name = "Wars";
        String description = "wars are everywhere";
        LocalDate releaseDate = LocalDate.of(2000,1,1);
        Film film = new Film();
        film.setName(name);
        film.setDescription(description);
        film.setReleaseDate(releaseDate);
        //when
        Duration duration = Duration.ofMinutes(0);
        film.setDuration(duration.toMinutes());
        //then
        assertThrows(InvalidFilmDataException.class, () -> {
            filmController.addFilm(film); });
    }

    @Test
    void addFilm_failure_invalidFilmDataExceptionBeforeINITIAL_DATE() throws InvalidFilmDataException,
            FilmAlreadyExistException {
        //given
        Long id = 1L;
        String name = "Wars";
        String description = "wars are everywhere";
        Duration duration = Duration.ofMinutes(100);
        Film film = new Film();
        film.setName(name);
        film.setDescription(description);
        film.setDuration(duration.toMinutes());
        //when
        LocalDate releaseDate = LocalDate.of(1894,1,1);
        film.setReleaseDate(releaseDate);
        //then
        assertThrows(InvalidFilmDataException.class, () -> {
            filmController.addFilm(film); });
    }

    @Test
    void addFilm_success_withEmptyDescription() throws InvalidFilmDataException, FilmAlreadyExistException {
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
    void addFilm_success_withNullDescription() throws InvalidFilmDataException, FilmAlreadyExistException {
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
    void updateFilm_success() throws InvalidFilmDataException, FilmAlreadyExistException {
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
        filmController.updateFilm(film);
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
    void updateFilm_failure_withNullId() throws InvalidFilmDataException, FilmAlreadyExistException {
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
        film.setId(null);
        //then
        assertThrows(ResponseStatusException.class, () -> {
            filmController.updateFilm(film);
        });
    }

    @Test
    void updateFilm_failure_withWrongId() throws InvalidFilmDataException, FilmAlreadyExistException {
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
        film.setId(-999L);
        //then
        assertThrows(ResponseStatusException.class, () -> {
            filmController.updateFilm(film);
        });
    }

    @Test
    void getFilms_success() throws InvalidFilmDataException, FilmAlreadyExistException {
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
    void getFilms_success_emptyCollection() throws InvalidFilmDataException, FilmAlreadyExistException {
        //when
        List<Film> films = filmController.getFilms();
        //then
        assertEquals(0, films.size());
    }
}