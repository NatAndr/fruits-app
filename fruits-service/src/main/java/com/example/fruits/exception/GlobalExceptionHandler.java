package com.example.fruits.exception;

import com.example.fruits.model.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    public static final String ERROR_OCCURRED = "An error occurred: ";

    @ExceptionHandler(value = FruitClientException.class)
    ResponseEntity<ErrorResponse> handleFruitClientException(FruitClientException ex, HttpServletRequest request) {
        log.error(ERROR_OCCURRED, ex);

        var response = ErrorResponse.builder()
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .error(ex.getLocalizedMessage())
            .message("Server error")
            .path(request.getRequestURI())
            .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex, HttpServletRequest request) {
        log.error(ERROR_OCCURRED, ex);

        var response = ErrorResponse.builder()
            .status(HttpStatus.NOT_FOUND.value())
            .error(ex.getLocalizedMessage())
            .message("Not found")
            .path(request.getRequestURI())
            .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAll(Exception ex, HttpServletRequest request) {
        log.error(ERROR_OCCURRED, ex);

        var response = ErrorResponse.builder()
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .error(ex.getLocalizedMessage())
            .message("Server error")
            .path(request.getRequestURI())
            .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
