package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;

import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.error.*;

@SpringBootTest
public class UserControllerTest {
    @Autowired
    private Validator validator;
    @Autowired
    private UserController userController;

    @BeforeEach
    void init() {
        userController = new UserController();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void userContextLoads_success() {
        assertNotNull(userController);
    }

    @Test
    void addUser_success() throws UserAlreadyExistException {
        //given
        Long id = 1L;
        String email = "user@user.com";
        String login = "userLogin";
        String name = "userName";
        LocalDate date = LocalDate.of(2000,1,1);
        User user = new User();
        user.setEmail(email);
        user.setLogin(login);
        user.setName(name);
        user.setBirthday(date);
        //when
        userController.addUser(user);
        List<User> users = userController.getUsers();
        User firstUser = users.get(0);
        //then
        assertNotNull(firstUser);
        assertEquals(id,firstUser.getId());
        assertEquals(email,firstUser.getEmail());
        assertEquals(login,firstUser.getLogin());
        assertEquals(name,firstUser.getName());
        assertEquals(date,firstUser.getBirthday());
    }

    @Test
    void addUser_failure_userAlreadyExistException() throws UserAlreadyExistException {
        //given
        String email = "user@user.com";
        String login = "userLogin";
        String name = "userName";
        LocalDate date = LocalDate.of(2000,1,1);
        User user = new User();
        user.setEmail(email);
        user.setLogin(login);
        user.setName(name);
        user.setBirthday(date);
        userController.addUser(user);
        //then
        assertThrows(UserAlreadyExistException.class, () -> {
            userController.addUser(user); });
    }

    @Test
    void addUser_success_withMalformedId() throws UserAlreadyExistException {
        Long id = -999L;
        String email = "user@user.com";
        String login = "userLogin";
        String name = "userName";
        LocalDate date = LocalDate.of(2000,1,1);
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setLogin(login);
        user.setName(name);
        user.setBirthday(date);
        //when
        userController.addUser(user);
        List<User> users = userController.getUsers();
        User firstUser = users.get(0);
        //then
        assertEquals(1L,firstUser.getId());
    }

    @Test
    void addUser_success_withEmptyName() throws UserAlreadyExistException {
        Long id = 1L;
        String email = "user@user.com";
        String login = "userLogin";
        String name = "";
        LocalDate date = LocalDate.of(2000,1,1);
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setLogin(login);
        user.setName(name);
        user.setBirthday(date);
        //when
        userController.addUser(user);
        List<User> users = userController.getUsers();
        User firstUser = users.get(0);
        //then
        assertEquals(login,firstUser.getName());
    }

    @Test
    void addUser_success_withNullLogin() throws UserAlreadyExistException {
        Long id = 1L;
        String email = "user@user.com";
        String login = "userLogin";
        String name = null;
        LocalDate date = LocalDate.of(2000,1,1);
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setLogin(login);
        user.setName(name);
        user.setBirthday(date);
        //when
        userController.addUser(user);
        List<User> users = userController.getUsers();
        User firstUser = users.get(0);
        //then
        assertEquals(login,firstUser.getName());
    }

    @Test
    void updateUser_success() throws UserAlreadyExistException {
        //given
        Long id = 1L;
        String email = "user@user.com";
        String login = "userLogin";
        String name = "userName";
        LocalDate date = LocalDate.of(2000,1,1);
        User user = new User();
        user.setEmail(email);
        user.setLogin(login);
        user.setName(name);
        user.setBirthday(date);
        userController.addUser(user);
        user.setId(id);
        String newLogin = "newLogin";
        user.setLogin(newLogin);
        //when
        userController.updateUser(user);
        List<User> users = userController.getUsers();
        User firstUser = users.get(0);
        //then
        assertNotNull(firstUser);
        assertEquals(id,firstUser.getId());
        assertEquals(email,firstUser.getEmail());
        assertEquals(newLogin,firstUser.getLogin());
        assertEquals(name,firstUser.getName());
        assertEquals(date,firstUser.getBirthday());
    }

    @Test
    void updateUser_failure_withNullId() throws UserAlreadyExistException {
        //given
        String email = "user@user.com";
        String login = "userLogin";
        String name = "userName";
        LocalDate date = LocalDate.of(2000, 1, 1);
        User user = new User();
        user.setEmail(email);
        user.setLogin(login);
        user.setName(name);
        user.setBirthday(date);
        userController.addUser(user);
        //when
        user.setId(null);
        //then
        assertThrows(ResponseStatusException.class, () -> {
            userController.updateUser(user);
        });
    }

    @Test
    void updateUser_failure_withWrongId() throws UserAlreadyExistException {
        //given
        String email = "user@user.com";
        String login = "userLogin";
        String name = "userName";
        LocalDate date = LocalDate.of(2000, 1, 1);
        User user = new User();
        user.setEmail(email);
        user.setLogin(login);
        user.setName(name);
        user.setBirthday(date);
        userController.addUser(user);
        //when
        user.setId(-999L);
        //then
        assertThrows(ResponseStatusException.class, () -> {
            userController.updateUser(user);
        });
    }

    @Test
    void getUsers_success() throws UserAlreadyExistException {
        //given
        String email = "user@user.com";
        String login = "userLogin";
        String name = "userName";
        LocalDate date = LocalDate.of(2000,1,1);
        User user = new User();
        user.setEmail(email);
        user.setLogin(login);
        user.setName(name);
        user.setBirthday(date);
        userController.addUser(user);
        String secondLogin = "seconduserLogin";
        user.setLogin(secondLogin);
        user.setId(null);
        userController.addUser(user);
        //when
        List<User> users = userController.getUsers();
        //then
        assertEquals(2, users.size());
    }

    @Test
    void getUsers_success_emptyCollection() throws InvalidFilmDataException, FilmAlreadyExistException {
        //when
        List<User> users = userController.getUsers();
        //then
        assertEquals(0, users.size());
    }
}