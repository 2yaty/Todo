package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.entity.Workspace;
import com.example.demo.exception.ApiRequestException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class WorkspaceService {
    private final WorkspaceRepository workspaceRepository;
    private final UserService userService;

    public Workspace createWorkspace(String name, String userMail) {
        User user = userService.findByEmail(userMail);


        Workspace workspace = new Workspace();
        workspace.setName(name);
        workspace.setUser(user);
        return workspaceRepository.save(workspace);

    }

    public Optional<Workspace> getById(Long workspaceId) {
        return workspaceRepository.findById(workspaceId);
    }

    public Workspace updateWorkspace(Long workspaceId, String name, String userMail) {
        if (!validateUser(workspaceId, userMail)) {
            throw new ApiRequestException("Workspace not found", ErrorCode.INVALID_WORKSPACE);
        }
        Workspace workspace = workspaceRepository.findById(workspaceId).orElseThrow();
        workspace.setName(name);
        return workspaceRepository.save(workspace);
    }

    public void deleteWorkspace(Long workspaceId, String userMail) {
        if (!validateUser(workspaceId, userMail)) {
            throw new ApiRequestException("Workspace not found", ErrorCode.INVALID_WORKSPACE);
        }
        workspaceRepository.deleteById(workspaceId);
    }

    public Optional<List<Workspace>> getWorkspacesByUserMail(String userMail) {
        return workspaceRepository.findAllByUserMail(userMail);
    }

    public boolean validateUser(Long workspaceId, String userMail) {
        return workspaceRepository.existsByIdAndUserMail(workspaceId, userMail);
    }
}
