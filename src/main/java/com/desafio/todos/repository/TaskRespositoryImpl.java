package com.desafio.todos.repository;

import com.desafio.todos.model.document.Task;
import com.example.model.Priority;
import com.example.model.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TaskRespositoryImpl {

    private final TaskRepository taskRepository;
    private final MongoTemplate mongoTemplate;

    public List<Task> filterTask(
            String title,
            String description,
            LocalDate expirationDate,
            Priority priority,
            Status status,
            Pageable pageable) {

        Query query = new Query();

        query.addCriteria(Criteria.where("deleted").is(false));

        if (title != null && !title.isEmpty()) {
            query.addCriteria(Criteria.where("title").regex(title, "i"));
        }

        if (description != null && !description.isEmpty()) {
            query.addCriteria(Criteria.where("description").regex(description, "i"));
        }

        if (expirationDate != null) {
            query.addCriteria(Criteria.where("expirationDate").is(expirationDate));
        }

        if (priority != null) {
            query.addCriteria(Criteria.where("priority").is(priority.getValue()));
        }

        if (status != null) {
            query.addCriteria(Criteria.where("status").is(status.getValue()));
        }

        query.with(pageable);

        return mongoTemplate.find(query, Task.class);

    }

    public Optional<Task> findById(String id) {
        return taskRepository.findByIdAndDeleted(id,false);
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

}
