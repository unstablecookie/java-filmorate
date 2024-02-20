package ru.yandex.practicum.filmorate.storage;

import java.util.List;
import ru.yandex.practicum.filmorate.exception.EntityAlreadyExistException;
import ru.yandex.practicum.filmorate.model.User;

public interface UserStorage {
    List<User> getUsers();

    User getUser(Long userId);

    User addUser(User user) throws EntityAlreadyExistException;

    User updateUser(Long userId, User user);
}