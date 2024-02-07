package ru.yandex.practicum.filmorate.error;

public class InvalidUserDataException extends Exception {
    public InvalidUserDataException() {
        super();
    }

    public InvalidUserDataException(String message) {
        super(message);
    }
}