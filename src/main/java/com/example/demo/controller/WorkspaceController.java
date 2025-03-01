package com.example.demo.controller;

import com.example.demo.dto.WorkSpaceResponse;
import com.example.demo.entity.Workspace;
import com.example.demo.service.UserService;
import com.example.demo.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/workspace")
public class WorkspaceController {
    private final WorkspaceService workspaceService;
    private final UserService userService;


    @PostMapping()
    public ResponseEntity<WorkSpaceResponse> createWorkspace(@RequestBody String name, Principal principal) {
        Workspace workspace = workspaceService.createWorkspace(name,principal.getName());
        return ResponseEntity.ok(new WorkSpaceResponse(workspace.getId(), workspace.getName()));

    }

    @GetMapping()
    public ResponseEntity<List<WorkSpaceResponse>> getUserWorkspaces(Principal principal) {
        return ResponseEntity.ok(workspaceService.getWorkspacesByUserMail(principal.getName()).orElseThrow().stream().map(workspace -> new WorkSpaceResponse(workspace.getId(), workspace.getName())).toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkSpaceResponse> updateWorkspace(@PathVariable Long id, @RequestBody String name, Principal principal) {
        Workspace workspace = workspaceService.updateWorkspace(id,name,principal.getName());
        return ResponseEntity.ok(new WorkSpaceResponse(workspace.getId(), workspace.getName()));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkspace(@PathVariable Long id, Principal principal) {
        workspaceService.deleteWorkspace(id,principal.getName());
        return ResponseEntity.noContent().build();
    }

}
