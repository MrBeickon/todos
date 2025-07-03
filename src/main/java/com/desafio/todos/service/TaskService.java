package com.desafio.todos.service;

import com.desafio.todos.model.document.Priority;
import com.desafio.todos.model.document.Status;
import com.desafio.todos.model.document.Task;
import com.desafio.todos.repository.TaskRespositoryImpl;
import com.desafio.todos.util.Result;
import com.example.model.TaskDto;
import com.example.model.TaskRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRespositoryImpl taskRespository;

    public List<TaskDto> tasksGet(
            String title,
            String description,
            LocalDate expirationDate,
            com.example.model.Priority priority,
            com.example.model.Status status,
            Integer page,
            Integer size) {


        Pageable pageable = PageRequest.of(
                Optional.ofNullable(page).orElse(0),
                Optional.ofNullable(size).orElse(10)
        );

        var taskCheck = Result.tryCatch(() -> taskRespository.filterTask(title, description, expirationDate, priority, status, pageable));

        var tasks = taskCheck.getData();

        return tasks.stream()
                .map(this::convertToDto)
                .toList();

    }

    public TaskDto getTaskById(String id) {

        var taskCheck = taskRespository.findById(id);

        return taskCheck
                .map(this::convertToDto)
                .orElseThrow();
    }

    public TaskDto setStatus(String id,Status status){

        var taskCheck = taskRespository.findById(id);

        return taskCheck
                .map(task -> {
                    task.setStatus(status);
                    var taskCompleted = taskRespository.save(task);

                    return this.convertToDto(taskCompleted);
                }).orElseThrow();
    }

    public TaskDto createTask(TaskRequest taskRequest) {

        var task = new Task();

        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setExpirationDate(taskRequest.getDueDate());
        task.setPriority(Priority.valueOf(taskRequest.getPriority().getValue()));
        task.setStatus(Status.PENDIENTE);

        var savedTask = taskRespository.save(task);

        return this.convertToDto(savedTask);
    }

    public TaskDto updateTask(String id, TaskRequest taskRequest) {


        var taskCheck = taskRespository.findById(id);

        return taskCheck
                .map(task -> {

                    task.setTitle(taskRequest.getTitle());
                    task.setDescription(taskRequest.getDescription());
                    task.setExpirationDate(taskRequest.getDueDate());
                    task.setPriority(Priority.valueOf(taskRequest.getPriority().getValue()));

                    var taskUpdated = taskRespository.save(task);

                    return this.convertToDto(taskUpdated);
                }).orElseThrow();
    }

    public void deleteTask(String id) {

        var taskCheck = taskRespository.findById(id);

        taskCheck.map(task -> {
            task.setDeleted(true);
            task.setDeletedAt(LocalDateTime.now());

            return taskRespository.save(task);
        }).orElseThrow();
    }

    private TaskDto convertToDto(Task task) {

        var priorityOptional = Optional.ofNullable(task.getPriority())
                .map(priority -> com.example.model.Priority.valueOf(priority.name()))
                .orElse(null);

        var statusOptional = Optional.ofNullable(task.getStatus())
                .map(status -> com.example.model.Status.valueOf(status.name()))
                .orElse(null);

        return new TaskDto()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .dueDate(task.getExpirationDate())
                .priority(priorityOptional)
                .status(statusOptional);
    }
}
