package com.crud.project_planner.repositories;

import com.crud.project_planner.models.entities.Project;
import com.crud.project_planner.models.entities.Task;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Task t SET t.status = true WHERE t.id = ?1")
    void softDeleteById(Long id);

    List<Task> findByStatus(Boolean status);
    Optional<Task> findByIdAndStatus(Long id, Boolean status);

    @Query("SELECT t FROM Task t WHERE t.name LIKE %:word% AND t.status = false")
    List<Task> findByNameContainingAndStatusZero(String word);
}
