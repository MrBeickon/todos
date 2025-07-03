package com.desafio.todos.handler;

import com.desafio.todos.model.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Component
public class ForbiddenExceptionHandlerStrategy implements ExceptionHandlerStrategy {

    @Override
    public boolean canHandle(Exception ex) {
        return ex instanceof HttpClientErrorException.Unauthorized;
    }

    @Override
    public ResponseEntity<ErrorResponse> handle(Exception ex) {
        HttpClientErrorException.Unauthorized exception = (HttpClientErrorException.Unauthorized) ex;

        ErrorResponse errorResponse = ErrorResponse.builder()
            .status(HttpStatus.FORBIDDEN.value())
            .cause("Acceso prohibido")
            .detail(exception.getMessage())
            .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }
}