package com.example.demo.controller;

import com.example.demo.dto.FullTaskResponse;
import com.example.demo.dto.ShortTaskResponse;
import com.example.demo.dto.TaskRequest;
import com.example.demo.entity.Task;
import com.example.demo.entity.Workspace;
import com.example.demo.exception.ApiRequestException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.service.TaskService;
import com.example.demo.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;
    private final WorkspaceService workspaceService;


    @PostMapping()
    public ResponseEntity<ShortTaskResponse> createTask(@RequestBody TaskRequest request) {
        Workspace workspace = workspaceService.getById(request.getWorkspaceId())
                .orElseThrow(() -> new ApiRequestException("Workspace not found", ErrorCode.INVALID_WORKSPACE));

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setWorkspace(workspace);
        task = taskService.createTask(task);

        return ResponseEntity.ok(new ShortTaskResponse(task.getId(), task.getTitle()));
    }

    @GetMapping()
    public ResponseEntity<List<ShortTaskResponse>> getTasks(@RequestParam Long workspaceId, Principal principal) {


        return ResponseEntity.ok(taskService.getTasksByWorkspaceId(workspaceId, principal.getName()).stream().map(task -> new ShortTaskResponse(task.getId(), task.getTitle())).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullTaskResponse> getTask(@PathVariable Long id, Principal principal) {
        Task task = taskService.getTaskById(id, principal.getName()).orElseThrow(() -> new ApiRequestException("Task not found", ErrorCode.INVALID_TASK));
        return ResponseEntity.ok(new FullTaskResponse(task.getId(), task.getTitle(), task.getDescription(), task.getStatus()));

    }


    @PatchMapping("/{id}/status")
    public ResponseEntity<ShortTaskResponse> updateTaskStatus(@PathVariable Long id, @RequestParam Task.TaskStatus status, Principal principal) {
        Task task = taskService.getTaskById(id,principal.getName()).orElseThrow(() -> new ApiRequestException("Task not found", ErrorCode.INVALID_TASK));
        task.setStatus(status);
        task = taskService.save(task, principal.getName());
        return ResponseEntity.ok(new ShortTaskResponse(task.getId(), task.getTitle()));
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteTask(@RequestParam Long taskId, Principal principal) {
        taskService.deleteTask(taskId,  principal.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
