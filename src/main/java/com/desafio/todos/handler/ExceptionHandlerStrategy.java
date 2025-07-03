package com.desafio.todos.handler;

import com.desafio.todos.model.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;

public interface ExceptionHandlerStrategy {
    boolean canHandle(Exception ex);
    ResponseEntity<ErrorResponse> handle(Exception ex);
}