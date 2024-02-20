package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.time.LocalDate;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.exception.EntityAlreadyExistException;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;

@SpringBootTest
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class UserControllerTest {
    @Autowired
    private UserController userController;

    @Autowired
    private UserStorage userStorage;

    @Autowired
    private UserService userService;

    @Test
    void userContextLoads_success() {
        assertNotNull(userController);
    }

    @Test
    void addUser_success() throws EntityAlreadyExistException {
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
    void addUser_failure_userAlreadyExistException() throws EntityAlreadyExistException {
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
        assertThrows(EntityAlreadyExistException.class, () -> {
            userController.addUser(user); });
    }

    @Test
    void addUser_success_withMalformedId() throws EntityAlreadyExistException {
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
    void addUser_success_withEmptyName() throws EntityAlreadyExistException {
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
    void addUser_success_withNullLogin() throws EntityAlreadyExistException {
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
    void updateUser_success() throws EntityAlreadyExistException {
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
        userController.updateUser(id, user);
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
    void updateUser_failure_withNullId() throws EntityAlreadyExistException {
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
        Long nullId = null;
        //then
        assertThrows(UserNotFoundException.class, () -> {
            userController.updateUser(nullId, user);
        });
    }

    @Test
    void updateUser_failure_withWrongId() throws EntityAlreadyExistException {
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
        Long wrongId = -999L;
        //then
        assertThrows(UserNotFoundException.class, () -> {
            userController.updateUser(wrongId, user);
        });
    }

    @Test
    void getUsers_success() throws EntityAlreadyExistException {
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
    void getUsers_success_emptyCollection() {
        //when
        List<User> users = userController.getUsers();
        //then
        assertEquals(0, users.size());
    }
}