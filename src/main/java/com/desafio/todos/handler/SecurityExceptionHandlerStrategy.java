package com.desafio.todos.handler;

import com.desafio.todos.model.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class SecurityExceptionHandlerStrategy implements ExceptionHandlerStrategy {

    @Override
    public boolean canHandle(Exception ex) {
        return ex instanceof SecurityException;
    }

    @Override
    public ResponseEntity<ErrorResponse> handle(Exception ex) {
        SecurityException exception = (SecurityException) ex;

        ErrorResponse errorResponse = ErrorResponse.builder()
            .status(HttpStatus.UNAUTHORIZED.value())
            .cause("No autorizado")
            .detail(exception.getMessage())
            .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}