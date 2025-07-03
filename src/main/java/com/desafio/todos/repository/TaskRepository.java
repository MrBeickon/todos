package com.desafio.todos.repository;

import com.desafio.todos.model.document.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TaskRepository extends MongoRepository<Task, String> {

    Optional<Task> findByIdAndDeleted(String id, boolean deleted);
}
