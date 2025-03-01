package com.example.demo.service;

import com.example.demo.entity.Task;
import com.example.demo.exception.ApiRequestException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final WorkspaceService workspaceService;

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getTasksByWorkspaceId(Long workspaceId, String userMail) {
        if (!workspaceService.validateUser(workspaceId, userMail)) {
            throw new ApiRequestException("Error: Workspace not found", ErrorCode.INVALID_WORKSPACE);
        }
        return taskRepository.findByWorkspaceId(workspaceId);
    }

    public Optional<Task> getTaskById(Long id, String userMail) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent() && !isTaskOwner(task.get(), userMail)) {
            throw new ApiRequestException("Error: Task not found", ErrorCode.INVALID_TASK);
        }
        return task;
    }

    public Task save(Task task, String userMail) {
        if (!isTaskOwner(task, userMail)) {
            throw new ApiRequestException("Error: Task not found", ErrorCode.INVALID_TASK);
        }
        return taskRepository.save(task);
    }

    public void deleteTask(Long taskId, String userMail) {
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isEmpty()) {
            throw new ApiRequestException("Error: Task not found", ErrorCode.INVALID_TASK);
        }
        taskRepository.deleteById(taskId);
    }

    private boolean isTaskOwner(Task task, String userMail) {
        return task.getWorkspace().getUser().getMail().equals(userMail);
    }
}
