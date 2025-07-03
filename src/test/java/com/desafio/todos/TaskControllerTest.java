package com.desafio.todos;

import com.desafio.todos.controller.TaskController;
import com.desafio.todos.service.TaskService;
import com.example.model.Priority;
import com.example.model.Status;
import com.example.model.TaskDto;
import com.example.model.TaskRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private TaskDto taskDto;
    private TaskRequest taskRequest;

    @BeforeEach
    void setUp() {
        taskDto = new TaskDto()
                .id("1")
                .title("Test Task")
                .description("Test Description")
                .dueDate(LocalDate.now())
                .priority(Priority.ALTA)
                .status(Status.PENDIENTE);

        taskRequest = new TaskRequest()
                .title("Test Task")
                .description("Test Description")
                .dueDate(LocalDate.now())
                .priority(Priority.ALTA);
    }

    @Test
    void tasksGet_Success() {
        when(taskService.tasksGet(any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(List.of(taskDto));

        var response = taskController.tasksGet(
                null, null, null, null, null, 0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void tasksPost_Success() {
        when(taskService.createTask(any())).thenReturn(taskDto);

        var response = taskController.tasksPost(taskRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(taskDto.getId(), response.getBody().getId());
    }

    @Test
    void tasksIdGet_Success() {
        when(taskService.getTaskById("1")).thenReturn(taskDto);

        var response = taskController.tasksIdGet("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(taskDto.getId(), response.getBody().getId());
    }

    @Test
    void tasksIdPut_Success() {
        when(taskService.updateTask(anyString(), any())).thenReturn(taskDto);

        var response = taskController.tasksIdPut("1", taskRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(taskDto.getId(), response.getBody().getId());
    }

    @Test
    void tasksIdDelete_Success() {
        doNothing().when(taskService).deleteTask(anyString());

        var response = taskController.tasksIdDelete("1");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(taskService, times(1)).deleteTask("1");
    }
}