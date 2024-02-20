package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.exception.EntityAlreadyExistException;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.UserStorage;

@RestController
@Slf4j
public class UserController {

    private final UserStorage userStorage;

    private final UserService userService;

    @Autowired
    public UserController(UserStorage userStorage, UserService userService) {
        this.userStorage = userStorage;
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        log.info("get all users");
        return userStorage.getUsers();
    }

    @PostMapping("/users")
    public User addUser(@Valid @RequestBody User user) throws EntityAlreadyExistException {
        log.info("add new user:" + user.getLogin());
        return userStorage.addUser(user);
    }

    @PutMapping(value = {"/users","/users/{userId}"})
    public User updateUser(@PathVariable(required = false) Long userId, @Valid @RequestBody User user) {
        return userStorage.updateUser(userId, user);
    }

    @GetMapping(value = "/users/{id}")
    public User getUser(@PathVariable(required = true) Long id) {
        log.info("get user id :" + id);
        return userStorage.getUser(id);
    }

    @GetMapping(value = "/users/{id}/friends")
    public List<User> getUserFriends(@PathVariable(required = true) Long id) {
        log.info("get user id :" + id + " friends");
        return userService.getFriends(id);
    }

    @PutMapping(value = "/users/{id}/friends/{friendId}")
    public void addFriend(@PathVariable(required = true) Long id,
                          @PathVariable(required = true) Long friendId) {
        log.info("add friend id :" + friendId + " to user id :" + id);
        userService.addFriend(id, friendId);
    }

    @DeleteMapping(value = "/users/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable(required = true) Long id,
                             @PathVariable(required = true) Long friendId) {
        log.info("remove friend id :" + friendId + " from user id :" + id);
        userService.removeFriend(id, friendId);
    }

    @GetMapping(value = "/users/{id}/friends/common/{friendId}")
    public List<User> getCommonFriends(@PathVariable(required = true) Long id,
                                       @PathVariable(required = true) Long friendId) {
        log.info("get common friends between user id :" + id + " and friend id :" + friendId);
        return userService.getCommonFriends(id, friendId);
    }
}