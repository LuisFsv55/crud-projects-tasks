package com.crud.project_planner.services.implementation;

import com.crud.project_planner.models.entities.Project;
import com.crud.project_planner.persistences.interfaces.IProjectDAO;
import com.crud.project_planner.services.interfaces.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements IProjectService {

    @Autowired
    private IProjectDAO projectDAO;


    @Override
    public Optional<Project> findByIdAndStatus(Long id, Boolean status) {
        return projectDAO.findByIdAndStatus(id, status);
    }

    @Override
    public List<Project> findByStatus(Boolean status) {
        return projectDAO.findByStatus(status);
    }

    @Override
    public void save(Project project) {
        projectDAO.save(project);
    }

    @Override
    public void softDeleteById(Long id) {
        projectDAO.softDeleteById(id);
    }

    @Override
    public List<Project> findByNameContaining(String word) {
        return projectDAO.findByNameContaining(word);
    }
}
