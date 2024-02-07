package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.time.LocalDate;
import ru.yandex.practicum.filmorate.error.*;

public class UserTest {
    private Validator validator;

    @BeforeEach
    void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validateUserEmail_failure_emptyEmail() throws UserAlreadyExistException {
        String email = "";
        String login = "userLogin";
        String name = "userName";
        LocalDate date = LocalDate.of(2000,1,1);
        User user = new User();
        user.setEmail(email);
        user.setLogin(login);
        user.setName(name);
        user.setBirthday(date);
        //when
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        //then
        assertFalse(violations.isEmpty());
    }

    @Test
    void validateUserEmail_failure_nullEmail() throws UserAlreadyExistException {
        String email = null;
        String login = "userLogin";
        String name = "userName";
        LocalDate date = LocalDate.of(2000,1,1);
        User user = new User();
        user.setEmail(email);
        user.setLogin(login);
        user.setName(name);
        user.setBirthday(date);
        //when
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        //then
        assertFalse(violations.isEmpty());
    }

    @Test
    void validateUserLogin_failure_emptyLogin() throws UserAlreadyExistException {
        String email = "user@user.com";
        String login = "";
        String name = "userName";
        LocalDate date = LocalDate.of(2000,1,1);
        User user = new User();
        user.setEmail(email);
        user.setLogin(login);
        user.setName(name);
        user.setBirthday(date);
        //when
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        //then
        assertFalse(violations.isEmpty());
    }

    @Test
    void validateUserLogin_failure_nullLogin() throws UserAlreadyExistException {
        String email = "user@user.com";
        String login = null;
        String name = "userName";
        LocalDate date = LocalDate.of(2000,1,1);
        User user = new User();
        user.setEmail(email);
        user.setLogin(login);
        user.setName(name);
        user.setBirthday(date);
        //when
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        //then
        assertFalse(violations.isEmpty());
    }

    @Test
    void validateUserBirthday_failure_futureBirthday() throws UserAlreadyExistException {
        String email = "user@user.com";
        String login = "userLogin";
        String name = "userName";
        LocalDate date = LocalDate.of(4000,1,1);
        User user = new User();
        user.setEmail(email);
        user.setLogin(login);
        user.setName(name);
        user.setBirthday(date);
        //when
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        //then
        assertFalse(violations.isEmpty());
    }

    @Test
    void validateUserBirthday_failure_nullBirthday() throws UserAlreadyExistException {
        String email = "user@user.com";
        String login = "userLogin";
        String name = "userName";
        LocalDate date = null;
        User user = new User();
        user.setEmail(email);
        user.setLogin(login);
        user.setName(name);
        user.setBirthday(date);
        //when
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        //then
        assertFalse(violations.isEmpty());
    }
}