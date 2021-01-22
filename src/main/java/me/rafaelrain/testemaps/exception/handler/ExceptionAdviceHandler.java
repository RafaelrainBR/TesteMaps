package me.rafaelrain.testemaps.exception.handler;

import me.rafaelrain.testemaps.exception.UserValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdviceHandler {

    @ExceptionHandler
    public ResponseEntity<?> handleException(UserValidationException e) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(e.getMessage());
    }
}
