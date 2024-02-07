package ru.yandex.practicum.filmorate.errors;

public class InvalidUserDataException extends Exception {
    public InvalidUserDataException() {
        super();
    }
    public InvalidUserDataException(String message) {
        super(message);
    }
}