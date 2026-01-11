package com.example.task_management.service;

import com.example.task_management.model.Task;
import com.example.task_management.repository.TaskRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    private final TaskRepository repo = mock(TaskRepository.class);
    private final TaskService service = new TaskServiceImpl(repo);

    @Test
    void shouldCreateTask() {
        when(repo.save(any())).thenAnswer(i -> i.getArgument(0));

        Task task = service.createTask(
                "Test", null, null, "2025-01-20");

        assertNotNull(task.getId());
        verify(repo).save(any());
    }

    @Test
    void shouldThrowWhenTaskNotFound() {
        when(repo.findById("1")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> service.getTask("1"));
    }
}
