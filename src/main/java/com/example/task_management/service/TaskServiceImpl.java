package com.example.task_management.service;

import com.example.task_management.exceptions.TaskDoesNotExistException;
import com.example.task_management.model.Task;
import com.example.task_management.model.TaskStatus;
import com.example.task_management.repository.TaskRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Data
@Service
public class TaskServiceImpl implements TaskService{

    private final TaskRepository repository;
    @Override
    public Task createTask(String title, String description, String status, String dueDate) {

        if (title == null || dueDate == null) {
            throw new IllegalArgumentException("Title and due_date are mandatory");
        }

        Task task = new Task(
                title,
                description,
                status == null ? null : TaskStatus.valueOf(status),
                LocalDate.parse(dueDate)
        );

        return repository.save(task);


    }

    @Override
    public Task getTask(String id) throws TaskDoesNotExistException {
        return repository.findById(id)
                .orElseThrow(() -> new TaskDoesNotExistException("Task does not exist"));
    }

    @Override
    public Task updateTask(String id, String title, String description, String status, String dueDate) throws TaskDoesNotExistException {
        Task task = getTask(id);

        task.update(
                title,
                description,
                status == null ? null : TaskStatus.valueOf(status),
                dueDate == null ? null : LocalDate.parse(dueDate)
        );

        return task;
    }

    @Override
    public void deleteTask(String id) throws TaskDoesNotExistException {
        if (!repository.existsById(id)) {
            throw new TaskDoesNotExistException("Task does not exist.");
        }
        repository.deleteById(id);
    }

    @Override
    public List<Task> listTasks(int page, int size) {

        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Invalid pagination parameters");
        }

        return repository.findAll()
                .stream()
                .sorted(Comparator.comparing(Task::getDueDate))
                .skip((long) page * size)
                .limit(size)
                .toList();
    }
}
