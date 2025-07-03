package com.desafio.todos.handler;

import com.desafio.todos.model.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class ResourceNotFoundHandlerStrategy implements ExceptionHandlerStrategy {

    @Override
    public boolean canHandle(Exception ex) {
        return ex instanceof NoSuchElementException;
    }

    @Override
    public ResponseEntity<ErrorResponse> handle(Exception ex) {
        NoSuchElementException exception = (NoSuchElementException) ex;

        ErrorResponse errorResponse = ErrorResponse.builder()
            .status(HttpStatus.NOT_FOUND.value())
            .cause("Recurso no encontrado")
            .detail(exception.getMessage())
            .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}