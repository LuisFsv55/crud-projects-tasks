package com.crud.project_planner.services.implementation;

import com.crud.project_planner.models.entities.Task;
import com.crud.project_planner.persistences.interfaces.ITaskDAO;
import com.crud.project_planner.services.interfaces.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements ITaskService {

    @Autowired
    private ITaskDAO taskDAO;


    @Override
    public Optional<Task> findByIdAndStatus(Long id, Boolean status) {
        return taskDAO.findByIdAndStatus(id, status);
    }

    @Override
    public List<Task> findByStatus(Boolean status) {
        return taskDAO.findByStatus(status);
    }

    @Override
    public List<Task> findByNameContaining(String word) {
        return taskDAO.findByNameContaining(word);
    }

    @Override
    public void save(Task task) {
        taskDAO.save(task);
    }

    @Override
    public void softDeleteById(Long id) {
        taskDAO.softDeleteById(id);
    }
}
