package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.EntityAlreadyExistException;
import ru.yandex.practicum.filmorate.model.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> mapOfUsers = new HashMap<>();
    private Long id = 0L;

    public List<User> getUsers() {
        return new ArrayList<>(mapOfUsers.values());
    }

    public User getUser(Long userId) {
        User user = mapOfUsers.get(userId);
        if (user == null) {
            throw new UserNotFoundException("user not found");
        }
        return user;
    }

    public User addUser(User user) throws EntityAlreadyExistException {
        userNameAutoCompletion(user);
        if ((user.getId() != null) && (mapOfUsers.get(user.getId()) != null)) {
            throw new EntityAlreadyExistException("user already exists");
        }
        user.setId(++id);
        mapOfUsers.put(user.getId(), user);
        return user;
    }

    public User updateUser(Long userId, User user) {
        if (userId == null) {
            userId = user.getId();
        }
        log.info("update user id: " + userId);
        if (userId == null || (mapOfUsers.get(userId) == null)) {
            throw new UserNotFoundException("user not found");
        }
        userNameAutoCompletion(user);
        mapOfUsers.put(user.getId(), user);
        return user;
    }

    private void userNameAutoCompletion(User user) {
        if ((user.getName() == null) || user.getName().equals("")) {
            user.setName(user.getLogin());
        }
    }
}