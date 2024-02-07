package ru.yandex.practicum.filmorate.errors;

public class InvalidFilmDataException extends Exception {
    public InvalidFilmDataException() {
        super();
    }
    public InvalidFilmDataException(String message) {
        super(message);
    }
}
