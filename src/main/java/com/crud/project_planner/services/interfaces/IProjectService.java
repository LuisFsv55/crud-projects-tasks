package com.crud.project_planner.services.interfaces;

import com.crud.project_planner.models.entities.Project;

import java.util.List;
import java.util.Optional;

public interface IProjectService {
    Optional<Project> findByIdAndStatus(Long id, Boolean status);
    List<Project> findByStatus(Boolean status);
    void save(Project project);
    void softDeleteById(Long id);
    List<Project> findByNameContaining(String word);
}
