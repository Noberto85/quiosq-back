package com.webone.quiosq.exception.handler;


import com.webone.quiosq.exception.MessageException;
import com.webone.quiosq.exception.NotFoundException;
import com.webone.quiosq.exception.SqlException;
import com.webone.quiosq.exception.UnauthorizedException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class ControllerExpectionHandler {



    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFound(NotFoundException ex) {
        final var builder = MessageException.builder()
            .cod(ex.getCode())
            .details(ex.getDetail())
            .message(ex.getMessage())
            .status(ex.getHttpStatus())
            .build();
        return ResponseEntity.status(ex.getHttpStatus()).body(builder);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
        MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(SqlException.class)
    public ResponseEntity<?> notFound(SqlException ex) {
        final var builder = MessageException.builder()
            .cod(ex.getCode())
            .details(ex.getDetail())
            .message(ex.getMessage())
            .status(ex.getHttpStatus())
            .build();
        return ResponseEntity.status(ex.getHttpStatus()).body(builder);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<?> notFound(HttpClientErrorException ex) {
        System.out.println("");
        return null;
    }

}



