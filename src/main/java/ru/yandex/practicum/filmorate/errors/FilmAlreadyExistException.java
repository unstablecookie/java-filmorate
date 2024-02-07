package ru.yandex.practicum.filmorate.errors;

public class FilmAlreadyExistException extends Exception {
    public FilmAlreadyExistException() {
        super();
    }
    public FilmAlreadyExistException(String message) {
        super(message);
    }
}