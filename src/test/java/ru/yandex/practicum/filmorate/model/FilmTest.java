package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.Duration;
import java.util.Set;
import java.time.LocalDate;
import ru.yandex.practicum.filmorate.error.*;

public class FilmTest {
    private Validator validator;

    @BeforeEach
    void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validateFilmDate_failure_dateInFuture() throws InvalidFilmDataException,
            FilmAlreadyExistException {
        //given
        String name = "Wars";
        String description = "wars are everywhere";
        LocalDate releaseDate = LocalDate.of(4000,1,1);
        Duration duration = Duration.ofMinutes(100);
        Film film = new Film();
        film.setDuration(duration.toMinutes());
        film.setName(name);
        film.setDescription(description);
        film.setReleaseDate(releaseDate);
        //when
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        //then
        assertFalse(violations.isEmpty());
    }

    @Test
    void validateFilmName_failure_emptyName() throws InvalidFilmDataException, FilmAlreadyExistException {
        //given
        String name = "";
        String description = "wars are everywhere";
        LocalDate releaseDate = LocalDate.of(2000,1,1);
        Duration duration = Duration.ofMinutes(100);
        Film film = new Film();
        film.setName(name);
        film.setDescription(description);
        film.setReleaseDate(releaseDate);
        film.setDuration(duration.toMinutes());
        //when
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        //then
        assertFalse(violations.isEmpty());
    }

    @Test
    void validateFilmName_failure_nullName() throws InvalidFilmDataException, FilmAlreadyExistException {
        //given
        String name = null;
        String description = "wars are everywhere";
        LocalDate releaseDate = LocalDate.of(2000,1,1);
        Duration duration = Duration.ofMinutes(100);
        Film film = new Film();
        film.setName(name);
        film.setDescription(description);
        film.setReleaseDate(releaseDate);
        film.setDuration(duration.toMinutes());
        //when
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        //then
        assertFalse(violations.isEmpty());
    }

    @Test
    void validateFilmDescription_failure_tooBigDescription() throws InvalidFilmDataException, FilmAlreadyExistException {
        //given
        String name = "Wars";
        String description = new String(new char[201]).replace('\0', ' ');
        LocalDate releaseDate = LocalDate.of(2000,1,1);
        Duration duration = Duration.ofMinutes(100);
        Film film = new Film();
        film.setName(name);
        film.setDescription(description);
        film.setReleaseDate(releaseDate);
        film.setDuration(duration.toMinutes());
        //when
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        //then
        assertFalse(violations.isEmpty());
    }
}