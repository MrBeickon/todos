package com.desafio.todos;

import com.desafio.todos.model.document.Priority;
import com.desafio.todos.model.document.Status;
import com.desafio.todos.model.document.Task;
import com.desafio.todos.repository.TaskRespositoryImpl;
import com.desafio.todos.service.TaskService;
import com.example.model.TaskRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRespositoryImpl taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;
    private TaskRequest taskRequest;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setId("1");
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setExpirationDate(LocalDate.now());
        task.setPriority(Priority.ALTA);
        task.setStatus(Status.PENDIENTE);

        taskRequest = new TaskRequest();
        taskRequest.setTitle("Test Task");
        taskRequest.setDescription("Test Description");
        taskRequest.setDueDate(LocalDate.now());
        taskRequest.setPriority(com.example.model.Priority.ALTA);
    }

    @Test
    void createTask_Success() {
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        var result = taskService.createTask(taskRequest);

        assertNotNull(result);
        assertEquals(task.getTitle(), result.getTitle());
        assertEquals(task.getDescription(), result.getDescription());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void getTaskById_Success() {
        when(taskRepository.findById("1")).thenReturn(Optional.of(task));

        var result = taskService.getTaskById("1");

        assertNotNull(result);
        assertEquals(task.getId(), result.getId());
        verify(taskRepository, times(1)).findById("1");
    }

    @Test
    void tasksGet_Success() {
        when(taskRepository.filterTask(
                any(), any(), any(), any(), any(), any(PageRequest.class)))
                .thenReturn(List.of(task));

        var result = taskService.tasksGet(
                null, null, null, null, null, 0, 10);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(taskRepository, times(1))
                .filterTask(any(), any(), any(), any(), any(), any());
    }

    @Test
    void setStatus_Success() {
        when(taskRepository.findById("1")).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        var result = taskService.setStatus("1", Status.COMPLETADA);

        assertNotNull(result);
        verify(taskRepository, times(1)).findById("1");
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void updateTask_Success() {
        when(taskRepository.findById("1")).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        var result = taskService.updateTask("1", taskRequest);

        assertNotNull(result);
        assertEquals(taskRequest.getTitle(), result.getTitle());
        verify(taskRepository, times(1)).findById("1");
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void deleteTask_Success() {
        when(taskRepository.findById("1")).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        assertDoesNotThrow(() -> taskService.deleteTask("1"));
        verify(taskRepository, times(1)).findById("1");
        verify(taskRepository, times(1)).save(any(Task.class));
    }
}