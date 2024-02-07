package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.error.UserAlreadyExistException;

@RestController
@Slf4j
public class UserController {
    private Map<Long, User> mapOfUsers = new HashMap<>();
    private Long id = 0L;

    @GetMapping("/users")
    public List<User> getUsers() {
        log.info("get all users");
        return List.of(mapOfUsers.values().toArray(new User[mapOfUsers.values().size()]));
    }

    @PostMapping("/users")
    public User addUser(@Valid @RequestBody User user) throws UserAlreadyExistException {
        log.debug("login : " + user.getLogin());
        log.info("add new user");
        userValidation(user);
        if ((user.getId() != null) && (mapOfUsers.get(user.getId()) != null)) {
            throw new UserAlreadyExistException("user already exists");
        }
        user.setId(++id);
        mapOfUsers.put(user.getId(), user);
        return user;
    }

    @PutMapping("/users")
    public User updateUser(@Valid @RequestBody User user) {
        log.info("update user");
        if (user.getId() == null || (mapOfUsers.get(user.getId()) == null)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "user not found"
            );
        }
        userValidation(user);
        mapOfUsers.put(user.getId(), user);
        return user;
    }

    private void userValidation(User user) {
        if ((user.getName() == null) || user.getName().equals("")) {
            user.setName(user.getLogin());
        }
    }
}
