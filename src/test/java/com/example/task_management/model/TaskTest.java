package com.example.task_management.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {
    @Test
    void shouldUpdateOnlyProvidedFields() {
        Task task = new Task("Title", "Desc", null, LocalDate.now());

        task.update(null, "Updated", TaskStatus.DONE, null);

        assertEquals("Title", task.getTitle());
        assertEquals("Updated", task.getDescription());
        assertEquals(TaskStatus.DONE, task.getStatus());
    }
}
