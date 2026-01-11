package com.example.task_management.controller;

import com.example.task_management.DTO.CreateTaskRequest;
import com.example.task_management.DTO.UpdateTaskRequest;
import com.example.task_management.exceptions.TaskDoesNotExistException;
import com.example.task_management.model.Task;
import com.example.task_management.service.TaskService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody CreateTaskRequest request) {
        Task task = service.createTask(
                request.title,
                request.description,
                request.status,
                request.due_date
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable String id) throws TaskDoesNotExistException {
        Task task = service.getTask(id);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable String id, @Valid @RequestBody UpdateTaskRequest request) throws TaskDoesNotExistException {
        Task updatedTask =  service.updateTask(
                id,
                request.title,
                request.description,
                request.status,
                request.due_date
        );

        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteTask(@PathVariable String id) throws TaskDoesNotExistException {
       service.deleteTask(id);
       return ResponseEntity.noContent().build();

    }

    @GetMapping
    public ResponseEntity<List<Task>> listTasks(@RequestParam (defaultValue = "0")int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.listTasks(page, size));
    }


}
