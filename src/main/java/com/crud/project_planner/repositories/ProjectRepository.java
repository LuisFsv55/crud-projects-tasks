package com.crud.project_planner.repositories;

import com.crud.project_planner.models.entities.Project;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Project p SET p.status = true WHERE p.id = ?1")
    void softDeleteById(Long id);

    List<Project> findByStatus(Boolean status);
    Optional<Project> findByIdAndStatus(Long id, Boolean status);

    @Query("SELECT p FROM Project p WHERE p.name LIKE %:word% AND p.status = false")
    List<Project> findByNameContainingAndStatusZero(String word);
}
