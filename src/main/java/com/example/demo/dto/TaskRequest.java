package com.example.demo.dto;

import lombok.Data;

@Data
public class TaskRequest {
    private String title;
    private String description;
    private Long workspaceId;
}
