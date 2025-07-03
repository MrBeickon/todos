package com.desafio.todos.handler;

import com.desafio.todos.model.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Component
public class ValidationExceptionHandlerStrategy implements ExceptionHandlerStrategy {

    @Override
    public boolean canHandle(Exception ex) {
        return ex instanceof MethodArgumentNotValidException;
    }

    @Override
    public ResponseEntity<ErrorResponse> handle(Exception ex) {
        MethodArgumentNotValidException exception = (MethodArgumentNotValidException) ex;

        // Obtiene el mensaje de error de la primera validación fallida
        String message = exception.getBindingResult().getFieldError() != null ?
                exception.getBindingResult().getFieldError().getField() + " " + exception.getBindingResult().getFieldError().getDefaultMessage()
                : "Error en los datos proporcionados";


        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .cause("Error en la entrada de datos.")
                .detail(message)
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}