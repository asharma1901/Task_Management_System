package com.example.task_management.service;

import com.example.task_management.exceptions.TaskDoesNotExistException;
import com.example.task_management.model.Task;

import java.util.List;

public interface TaskService {

    Task createTask(String title, String description, String status, String dueDate);

    Task getTask(String id) throws TaskDoesNotExistException;

    Task updateTask(String id, String title, String description, String status, String dueDate) throws TaskDoesNotExistException;

    void deleteTask(String id) throws TaskDoesNotExistException;

    List<Task> listTasks(int page, int size);
}
