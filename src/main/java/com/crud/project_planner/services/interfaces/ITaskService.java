package com.crud.project_planner.services.interfaces;

import com.crud.project_planner.models.entities.Task;

import java.util.List;
import java.util.Optional;

public interface ITaskService {
    Optional<Task> findByIdAndStatus(Long id, Boolean status);
    List<Task> findByStatus(Boolean status);
    List<Task> findByNameContaining(String word);
    void save(Task project);
    void softDeleteById(Long id);
}
