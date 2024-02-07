package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

@Data
public class User {
    private Long id;
    @NotNull
    @Email(regexp = ".+@.+\\..+", message = "wrong email format")
    String email;
    @NotNull(message = "login cannot be null")
    @NotBlank(message = "login cannot be blank")
    String login;
    String name;
    @NotNull
    @Past
    LocalDate birthday;
}