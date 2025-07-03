package com.desafio.todos.model.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@Document(collection = "tasks")
public class Task extends BaseDocument{
    @Id
    private String id;

    private String title;

    private String description;

    private LocalDate expirationDate;

    private Priority priority;

    private Status status;

}