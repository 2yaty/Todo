package com.example.demo.repository;

import com.example.demo.entity.User;
import com.example.demo.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
    List<Workspace> findByUser(User user);


    Optional<List<Workspace>> findAllByUserMail(String userMail);

    boolean existsByIdAndUserMail(Long workspaceId, String userMail);
}
