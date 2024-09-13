package com.crud.project_planner.persistences.implementation;

import com.crud.project_planner.models.entities.Project;
import com.crud.project_planner.persistences.interfaces.IProjectDAO;
import com.crud.project_planner.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProjectDAOImpl implements IProjectDAO {

    @Autowired
    private ProjectRepository projectRepository;


    @Override
    public Optional<Project> findByIdAndStatus(Long id, Boolean status) {
        return projectRepository.findByIdAndStatus(id, status);
    }

    @Override
    public List<Project> findByStatus(Boolean status) {
        return projectRepository.findByStatus(status);
    }

    @Override
    public void save(Project project) {
        projectRepository.save(project);
    }

    @Override
    public void softDeleteById(Long id) {
        projectRepository.softDeleteById(id);
    }

    @Override
    public List<Project> findByNameContaining(String word) {
        return projectRepository.findByNameContainingAndStatusZero(word);
    }
}
