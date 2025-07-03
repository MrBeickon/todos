package com.desafio.todos.controller;

import com.desafio.todos.service.TaskService;
import com.example.api.TasksApi;
import com.example.model.Priority;
import com.example.model.Status;
import com.example.model.TaskDto;
import com.example.model.TaskRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class TaskController implements TasksApi {

    private final TaskService taskService;

    @Override
    public ResponseEntity<List<TaskDto>> tasksGet(String title,
                                                  String description,
                                                  LocalDate expirationDate,
                                                  Priority priority,
                                                  Status status,
                                                  Integer page,
                                                  Integer size) {
        return ResponseEntity.ok(taskService.tasksGet(title,
                description,
                expirationDate,
                priority,
                status,
                page,
                size));
    }
    
    @Override
    public ResponseEntity<TaskDto> tasksIdGet(String id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @Override
    public ResponseEntity<TaskDto> tasksIdCompletePatch(String id){
        return ResponseEntity.ok(taskService.setStatus(id, com.desafio.todos.model.document.Status.COMPLETADA));
    }

    @Override
    public ResponseEntity<TaskDto> tasksIdStartPatch(String id){
        return ResponseEntity.ok(taskService.setStatus(id, com.desafio.todos.model.document.Status.COMPLETADA));
    }
    
    @Override
    public ResponseEntity<TaskDto> tasksPost(TaskRequest request) {
        return ResponseEntity.ok(taskService.createTask(request));
    }
    
    @Override
    public ResponseEntity<TaskDto> tasksIdPut(String id, TaskRequest request) {
        return ResponseEntity.ok(taskService.updateTask(id, request));
    }
    
    @Override
    public ResponseEntity<Void> tasksIdDelete(String id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
