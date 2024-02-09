package ru.yandex.practicum.filmorate.error;

public class EntityAlreadyExistException extends Exception {
    public EntityAlreadyExistException() {
        super();
    }

    public EntityAlreadyExistException(String message) {
        super(message);
    }
}
