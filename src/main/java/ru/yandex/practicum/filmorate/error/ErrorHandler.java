package ru.yandex.practicum.filmorate.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    public ResponseEntity<JsonResponse> handleUserNotFound(final UserNotFoundException e) {
        return new ResponseEntity<JsonResponse>(new JsonResponse(e.getMessage()), HttpStatus.NOT_FOUND) ;
    }

    @ExceptionHandler
    public ResponseEntity<JsonResponse> handleFilmNotFound(final FilmNotFoundException e) {
        return new ResponseEntity<JsonResponse>(new JsonResponse(e.getMessage()), HttpStatus.NOT_FOUND) ;
    }

    private class JsonResponse {
        String message;

        public JsonResponse() {
        }

        public JsonResponse(String message) {
            super();
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}