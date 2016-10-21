package com.allie.data.handler;

import com.allie.data.dto.Error;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by andrew.larsen on 10/20/2016.
 */
@ControllerAdvice
public class CustomExceptionHandler  extends ResponseEntityExceptionHandler{
    public static final String DEFAULT_ERROR_VIEW = "error";

    @ResponseStatus(HttpStatus.CONFLICT)  // 409
    @ExceptionHandler({DataIntegrityViolationException.class, DuplicateKeyException.class})
    public ResponseEntity<com.allie.data.dto.Error> handleConflict(Exception e) {
        Error error = new Error();
        error.setError(HttpStatus.CONFLICT.getReasonPhrase());
        error.setMessage(e.getMessage());
        error.setStatus(HttpStatus.CONFLICT.value());
        error.setTimestamp(System.currentTimeMillis());
        error.setPath("/");


        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST) //400
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<com.allie.data.dto.Error> handleBadRequest(Exception e) {
        Error error = new Error();
        error.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        error.setMessage(e.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setTimestamp(System.currentTimeMillis());
        error.setPath("/");


        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
