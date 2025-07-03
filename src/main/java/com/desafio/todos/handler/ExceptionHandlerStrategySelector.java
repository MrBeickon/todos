package com.desafio.todos.handler;

import com.desafio.todos.model.dto.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExceptionHandlerStrategySelector {

    private final List<ExceptionHandlerStrategy> strategies;

    @Autowired
    public ExceptionHandlerStrategySelector(List<ExceptionHandlerStrategy> strategies) {
        this.strategies = strategies;
    }

    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        return strategies.stream()
            .filter(strategy -> strategy.canHandle(ex)) // Encuentra quién puede manejar la excepción
            .findFirst()
            .map(strategy -> strategy.handle(ex)) // Ejecuta el manejo de la excepción
            .orElseGet(() -> handleUnknownException(ex)); // Manejo genérico si no existe estrategia
    }

    private ResponseEntity<ErrorResponse> handleUnknownException(Exception ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
            .status(500)
            .cause("Error interno del servidor")
            .detail("Ocurrió un error inesperado. Por favor, contacte al soporte técnico.")
            .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}