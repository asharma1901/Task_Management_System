package com.example.task_management.DTO;

import jakarta.validation.constraints.Future;

public class UpdateTaskRequest {

    public String title;
    public String description;
    public String status;

    @Future(message = "Due date must be in the future")
    public String due_date;
}
