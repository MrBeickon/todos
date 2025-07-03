package com.desafio.todos.handler;

import com.desafio.todos.model.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

@Component
public class BadCredentialsHandlerStrategy implements ExceptionHandlerStrategy {

    @Override
    public boolean canHandle(Exception ex) {
        return ex instanceof BadCredentialsException;
    }

    @Override
    public ResponseEntity<ErrorResponse> handle(Exception ex) {
        BadCredentialsException exception = (BadCredentialsException) ex;

        ErrorResponse errorResponse = ErrorResponse.builder()
            .status(HttpStatus.UNAUTHORIZED.value())
            .cause("Usuario o contraseña incorrectos")
            .detail(exception.getMessage())
            .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}