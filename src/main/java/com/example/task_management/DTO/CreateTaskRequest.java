package com.example.task_management.DTO;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateTaskRequest {

    @NotBlank(message = "Title is mandatory")
    public String title;
    public String description;

    public String status;

    @NotNull(message = "due_date is mandatory")
    public String due_date;
}
