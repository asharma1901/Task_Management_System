package com.example.task_management.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TaskDoesNotExistException extends Exception {
    public TaskDoesNotExistException(String taskDoesNotExist) {
        super(taskDoesNotExist);
    }
}
