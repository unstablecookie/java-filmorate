package ru.yandex.practicum.filmorate.util;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@NotNull
@Email(message="enter valid email address")
@Pattern(regexp=".+@.+\\..+", message="enter valid email address")
@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface CustomEmailValidator {
    String message() default "enter valid email address";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}