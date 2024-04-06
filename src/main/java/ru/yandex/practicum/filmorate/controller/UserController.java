package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.exception.EntityAlreadyExistException;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.util.LogThis;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    @LogThis
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping
    @LogThis
    public User addUser(@Valid @RequestBody User user) throws EntityAlreadyExistException {
        return userService.addUser(user);
    }

    @PutMapping(value = {"","/{userId}"})
    @LogThis
    public User updateUser(@PathVariable(required = false) Long userId, @Valid @RequestBody User user) {
        return userService.updateUser(userId, user);
    }

    @GetMapping(value = "/{id}")
    @LogThis
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping(value = "/{id}/friends")
    @LogThis
    public Set<User> getUserFriends(@PathVariable Long id) {
        return userService.getFriends(id);
    }

    @PutMapping(value = "/{id}/friends/{friendId}")
    @LogThis
    public void addFriend(@PathVariable Long id,
                          @PathVariable Long friendId) {
        userService.addFriend(id, friendId);
    }

    @DeleteMapping(value = "/{id}/friends/{friendId}")
    @LogThis
    public void removeFriend(@PathVariable Long id,
                             @PathVariable Long friendId) {
        userService.removeFriend(id, friendId);
    }

    @GetMapping(value = "/{id}/friends/common/{friendId}")
    @LogThis
    public List<User> getCommonFriends(@PathVariable Long id,
                                       @PathVariable Long friendId) {
        return userService.getCommonFriends(id, friendId);
    }

    @PutMapping(value = "/{id}/friends/common/{friendId}")
    @LogThis
    public void approveFriend(@PathVariable Long id,
                              @PathVariable Long friendId) {
        userService.approveFriend(id, friendId);
    }
}