package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.error.EntityAlreadyExistException;

@RestController
@Slf4j
public class UserController {
    private Map<Long, User> mapOfUsers = new HashMap<>();
    private Long id = 0L;

    @GetMapping("/users")
    public List<User> getUsers() {
        log.info("get all users");
        return new ArrayList<>(mapOfUsers.values());
    }

    @PostMapping("/users")
    public User addUser(@Valid @RequestBody User user) throws EntityAlreadyExistException {
        log.info("add new user:" + user.getLogin());
        userNameAutoCompletion(user);
        if ((user.getId() != null) && (mapOfUsers.get(user.getId()) != null)) {
            throw new EntityAlreadyExistException("user already exists");
        }
        user.setId(++id);
        mapOfUsers.put(user.getId(), user);
        return user;
    }

    @PutMapping(value = {"/users","/users/{userId}"})
    public User updateUser(@PathVariable(required = false) Long userId, @Valid @RequestBody User user) {
        if (userId == null) {
            userId = user.getId();
        }
        log.info("update user id: " + userId);
        if (userId == null || (mapOfUsers.get(userId) == null)) {
            
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "user not found"
            );
        }
        user.setId(userId);
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
