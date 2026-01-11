package com.example.task_management.DTO;

import jakarta.validation.constraints.Future;

public class UpdateTaskRequest {

    public String title;
    public String description;
    public String status;

    public String due_date;
}
