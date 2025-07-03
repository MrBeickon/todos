package com.desafio.todos.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    private int status;
    private String cause;
    private String detail;

}