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
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.errors.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.errors.InvalidUserDataException;

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
        if (mapOfUsers.get(user.getId()) != null) {
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

    public void emailValidation(String email) throws InvalidUserDataException {
        if ((email == null) || email.equals("") || (email.split("@").length < 2)) {
            throw new InvalidUserDataException("wrong or empty email field");
        }
    }
    
    public void userLoginValidation(String login) throws InvalidUserDataException {
        if ((login == null) || login.equals("") || (login.split(" ").length > 1)) {
            log.debug("login == null : " + (login == null));
            log.debug("login.equals(\"\") : " + login.equals(""));
            log.debug("(login.split(\" \").length > 0) : " + (login.split(" ").length > 0));
            throw new InvalidUserDataException("wrong user login");
        }
    }
    
    public void userBirthdayValidation(LocalDate date) throws InvalidUserDataException {
        if (date.isAfter(LocalDate.now())) {
            throw new InvalidUserDataException("wrong birthday date");
        }
    }

    public void userValidation(User user) {
//        emailValidation(user.getEmail());
//        userLoginValidation(user.getLogin());
//        userBirthdayValidation(user.getBirthday());
        if ((user.getName() == null) || user.getName().equals("")) {
            user.setName(user.getLogin());
        }
    }
}
