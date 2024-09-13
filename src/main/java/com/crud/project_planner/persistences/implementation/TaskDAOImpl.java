package com.crud.project_planner.persistences.implementation;

import com.crud.project_planner.models.entities.Task;
import com.crud.project_planner.persistences.interfaces.ITaskDAO;
import com.crud.project_planner.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskDAOImpl implements ITaskDAO {

    @Autowired
    private TaskRepository taskRepository;


    @Override
    public Optional<Task> findByIdAndStatus(Long id, Boolean status) {
        return taskRepository.findByIdAndStatus(id, status);
    }

    @Override
    public List<Task> findByStatus(Boolean status) {
        return taskRepository.findByStatus(status);
    }

    @Override
    public List<Task> findByNameContaining(String word) {
        return taskRepository.findByNameContainingAndStatusZero(word);
    }

    @Override
    public void save(Task project) {
        taskRepository.save(project);
    }

    @Override
    public void softDeleteById(Long id) {
        taskRepository.softDeleteById(id);
    }
}
