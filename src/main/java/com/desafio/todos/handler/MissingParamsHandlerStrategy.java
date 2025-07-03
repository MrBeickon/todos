package com.desafio.todos.handler;

import com.desafio.todos.model.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MissingServletRequestParameterException;

@Component
public class MissingParamsHandlerStrategy implements ExceptionHandlerStrategy {

    @Override
    public boolean canHandle(Exception ex) {
        return ex instanceof MissingServletRequestParameterException;
    }

    @Override
    public ResponseEntity<ErrorResponse> handle(Exception ex) {
        MissingServletRequestParameterException exception = (MissingServletRequestParameterException) ex;

        ErrorResponse errorResponse = ErrorResponse.builder()
            .status(HttpStatus.BAD_REQUEST.value())
            .cause("Parámetro faltante")
            .detail(exception.getMessage())
            .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}