package com.allie.data.handler;

import com.allie.data.dto.Error;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by andrew.larsen on 10/20/2016.
 */
@ControllerAdvice
public class CustomerExceptionHandler  extends ResponseEntityExceptionHandler{
    public static final String DEFAULT_ERROR_VIEW = "error";

    @ResponseStatus(HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<com.allie.data.dto.Error> handleConflict(Exception e) {
        Error error = new Error();
        error.setError(HttpStatus.CONFLICT.getReasonPhrase());
        error.setMessage("Allie user exists");
        error.setStatus(HttpStatus.CONFLICT.value());
        error.setTimestamp(System.currentTimeMillis());
        error.setPath("/");


        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

}
