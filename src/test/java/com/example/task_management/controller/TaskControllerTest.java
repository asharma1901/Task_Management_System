package com.example.task_management.controller;


import com.example.task_management.DTO.CreateTaskRequest;
import com.example.task_management.DTO.UpdateTaskRequest;
import com.example.task_management.exceptions.TaskDoesNotExistException;
import com.example.task_management.model.Task;
import com.example.task_management.model.TaskStatus;
import com.example.task_management.service.TaskService;
import org.apache.coyote.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class TaskControllerTest {

    private TaskService taskService;
    private TaskController taskController;

    @BeforeEach
    void setUp()
    {
        taskService = Mockito.mock(TaskService.class);
        taskController = new TaskController(taskService);

    }

    @Test
    void testCreateTask() {
        Task mockTask = new Task(
                "Test Task",
                "Description",
                TaskStatus.PENDING,
                LocalDate.now()
        );

        Mockito.when(taskService.createTask(
                anyString(), anyString(), anyString(), anyString()
        )).thenReturn(mockTask);

        CreateTaskRequest request = new CreateTaskRequest();
        request.title = "Test Task";
        request.description = "Description";
        request.status = "PENDING";
        request.due_date = "2025-01-20";

        var response = taskController.createTask(request);

        assertEquals(201, response.getStatusCode().value());
        assertEquals("Test Task", response.getBody().getTitle());
    }

    @Test
    void testGetTask() throws TaskDoesNotExistException {
        Task mockTask = new Task(
                "cardio","cardio exercise",
                TaskStatus.PENDING,
                LocalDate.parse("2025-12-19"));

        when(taskService.getTask("1")).thenReturn(mockTask);

        ResponseEntity<Task> result = taskController.getTask("1");

        assertEquals(200, result.getStatusCode().value());
        assertEquals("cardio", result.getBody().getTitle());

    }

    @Test
    void testUpdateTask() throws TaskDoesNotExistException {

        Task updatedTask = new Task(
                "Updated Task",
                "Updated Desc",
                TaskStatus.DONE,
                LocalDate.now()
        );

        Mockito.when(taskService.updateTask(
                anyString(), any(), any(), any(), any()
        )).thenReturn(updatedTask);

        UpdateTaskRequest request = new UpdateTaskRequest();
        request.title = "Updated Task";
        request.description = "Updated Desc";
        request.status = "DONE";
        request.due_date = "2025-01-20";

        ResponseEntity<Task> result = taskController.updateTask("1", request);

        assertEquals("Updated Task", result.getBody().getTitle());
        assertEquals(TaskStatus.DONE, result.getBody().getStatus());
    }


    @Test
    void testListTasks() {

        List<Task> mockTasks = List.of(
                new Task("Task1", null, null, LocalDate.of(2025, 1, 10)),
                new Task("Ta" +
                        "sk2", null, null, LocalDate.of(2025, 1, 20))
        );

        Mockito.when(taskService.listTasks(0,1)).thenReturn(List.of(mockTasks.get(0)));

        ResponseEntity<List<Task>> result = taskController.listTasks(0,1);

        assertEquals(1, result.getBody().size());
        assertEquals("Task1", result.getBody().get(0).getTitle());
    }

    @Test
    void testDeleteTask() throws TaskDoesNotExistException {
        ResponseEntity<Void> response = taskController.deleteTask("1");
        assertEquals(204, response.getStatusCode().value());
    }

    @Test
    void testGetTask_NotFound() throws TaskDoesNotExistException {
        Mockito.when(taskService.getTask("1"))
                .thenThrow(new TaskDoesNotExistException("Task not found"));

        try {
            taskController.getTask("1");
        } catch (TaskDoesNotExistException e) {
            assertEquals("Task not found", e.getMessage());
        }
    }

}
