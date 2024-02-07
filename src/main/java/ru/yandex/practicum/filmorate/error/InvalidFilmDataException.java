package ru.yandex.practicum.filmorate.error;

public class InvalidFilmDataException extends Exception {
    public InvalidFilmDataException() {
        super();
    }

    public InvalidFilmDataException(String message) {
        super(message);
    }
}
