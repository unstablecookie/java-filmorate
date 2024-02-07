package ru.yandex.practicum.filmorate.error;

public class FilmAlreadyExistException extends Exception {
    public FilmAlreadyExistException() {
        super();
    }

    public FilmAlreadyExistException(String message) {
        super(message);
    }
}