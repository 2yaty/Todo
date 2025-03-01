package com.example.demo.dto;

import com.example.demo.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FullTaskResponse {
    private long id;
    private String title;
    private String description;
    private Task.TaskStatus status;
}
