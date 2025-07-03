package com.desafio.todos.handler;

import com.desafio.todos.model.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;

@Component
public class HttpMessageNotReadableHandlerStrategy implements ExceptionHandlerStrategy {

    @Override
    public boolean canHandle(Exception ex) {
        return ex instanceof HttpMessageNotReadableException;
    }

    @Override
    public ResponseEntity<ErrorResponse> handle(Exception ex) {
        HttpMessageNotReadableException exception = (HttpMessageNotReadableException) ex;

        // Obtiene el mensaje de error de la primera validación fallida
        String message = exception.getMessage().split(",")[1];

        ErrorResponse errorResponse = ErrorResponse.builder()
            .status(HttpStatus.BAD_REQUEST.value())
            .cause("Problema en el formato de los datos")
            .detail(message)
            .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}