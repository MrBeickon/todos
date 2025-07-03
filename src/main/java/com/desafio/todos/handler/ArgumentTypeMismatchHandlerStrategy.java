package com.desafio.todos.handler;

import com.desafio.todos.model.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Component
public class ArgumentTypeMismatchHandlerStrategy implements ExceptionHandlerStrategy {

    @Override
    public boolean canHandle(Exception ex) {
        return ex instanceof MethodArgumentTypeMismatchException;
    }

    @Override
    public ResponseEntity<ErrorResponse> handle(Exception ex) {
        MethodArgumentTypeMismatchException exception = (MethodArgumentTypeMismatchException) ex;

        ErrorResponse errorResponse = ErrorResponse.builder()
            .status(HttpStatus.BAD_REQUEST.value())
            .cause("Tipo de dato inválido")
            .detail(String.format(
                "El parámetro '%s' recibió el valor '%s', que no es válido. Se esperaba un '%s'.",
                exception.getName(),
                exception.getValue(),
                exception.getRequiredType() != null ? exception.getRequiredType().getSimpleName() : "desconocido"))
            .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}