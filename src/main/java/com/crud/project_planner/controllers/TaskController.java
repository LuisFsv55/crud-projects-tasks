package com.crud.project_planner.controllers;

import com.crud.project_planner.models.dto.TaskDTO;
import com.crud.project_planner.models.entities.Project;
import com.crud.project_planner.models.entities.Task;
import com.crud.project_planner.models.request.WordRequest;
import com.crud.project_planner.services.interfaces.IProjectService;
import com.crud.project_planner.services.interfaces.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    @Autowired
    private ITaskService taskService;
    @Autowired
    private IProjectService projectService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Task> taskOptional = taskService.findByIdAndStatus(id, false);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            TaskDTO taskDTO = TaskDTO.builder()
                    .id(task.getId())
                    .name(task.getName())
                    .date(task.getDate())
                    .project(task.getProject())
                    .status(task.getStatus())
                    .build();
            return ResponseEntity.ok(taskDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap("message", "Tarea no encontrada"));
    }
    @GetMapping("/search")
    public ResponseEntity<?> findByName(@RequestBody WordRequest wordRequest) {
        Map<String, Object> response = new HashMap<>();
        if (wordRequest == null || wordRequest.getWord() == null) {
            response.put("message", "El parámetro 'word' es obligatorio y no puede estar vacío.");
            return ResponseEntity.badRequest().body(response);
        }
        String word = wordRequest.getWord().trim();
        if (word.isEmpty()) {
            response.put("message", "El parámetro 'word' no puede estar vacío.");
            return ResponseEntity.badRequest().body(response);
        }
        List<Task> tasks = taskService.findByNameContaining(word);
        if (tasks.isEmpty()) {
            response.put("message", "No se encontraron tareas con la palabra.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        List<TaskDTO> taskDTOList = tasks.stream()
                .map(task -> TaskDTO.builder()
                        .id(task.getId())
                        .name(task.getName())
                        .date(task.getDate())
                        .project(task.getProject())
                        .status(task.getStatus())
                        .build())
                .toList();

        return ResponseEntity.ok(taskDTOList);
    }
    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        List<TaskDTO> taskDTOList = taskService.findByStatus(false).stream()
                .map(task -> TaskDTO.builder()
                        .id(task.getId())
                        .name(task.getName())
                        .date(task.getDate())
                        .project(task.getProject())
                        .status(task.getStatus())
                        .build()
                ).toList();
        return ResponseEntity.ok(taskDTOList);
    }
    @PostMapping("/save")
    public ResponseEntity<?> save( @RequestBody TaskDTO taskDTO ) throws URISyntaxException {
        if (taskDTO.getName() == null || taskDTO.getName().isBlank()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "El campo 'name' es obligatorio y no puede estar vacío.");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        if (taskDTO.getDate() == null) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "El campo 'date' es obligatorio.");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        if (taskDTO.getProject() == null) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "El campo 'project'");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        Optional<Project> taskOptional = projectService.findByIdAndStatus(taskDTO.getProject().getId(), false);
        if (!taskOptional.isPresent()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "El campo 'project' no existe");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        Task task = Task.builder()
                .name(taskDTO.getName())
                .date(taskDTO.getDate())
                .project(taskDTO.getProject())
                .status(false)
                .build();
        taskService.save(task);
        Map<String, String> successResponse = new HashMap<>();
        successResponse.put("message", "Tarea creado correctamente.");
        return ResponseEntity.created(new URI("/api/task/save")).body(successResponse);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update( @PathVariable Long id, @RequestBody TaskDTO taskDTO ) throws URISyntaxException {
        Map<String, String> errorResponse = new HashMap<>();

        Optional<Task> taskOptional = taskService.findByIdAndStatus(id, false);
        if (!taskOptional.isPresent()) {
            errorResponse.put("message", "El id de la tarea no existe");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        if (taskDTO.getName() == null || taskDTO.getName().isBlank()) {
            errorResponse.put("message", "El campo 'name' es obligatorio y no puede estar vacío.");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        if (taskDTO.getDate() == null) {
            errorResponse.put("message", "El campo 'date' es obligatorio.");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        if (taskDTO.getProject() == null) {
            errorResponse.put("message", "El campo 'project'");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        Optional<Project> projectOptional = projectService.findByIdAndStatus(taskDTO.getProject().getId(), false);
        if (!projectOptional.isPresent()) {
            errorResponse.put("message", "El id del proyecto no existe");
            return ResponseEntity.badRequest().body(errorResponse);
        }


        Task task = taskOptional.get();
        task.setName(taskDTO.getName());
        task.setDate(taskDTO.getDate());
        task.setProject(taskDTO.getProject());
        task.setStatus(false);
        taskService.save(task);

        Map<String, String> successResponse = new HashMap<>();
        successResponse.put("message", "Tarea actualizada correctamente.");
        return ResponseEntity.created(new URI("/api/task/update")).body(successResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        if ( id == null ) {
            response.put("message", "El ID no puede ser nulo.");
            return ResponseEntity.badRequest().body(response);
        }
        Optional<Task> taskOptional = taskService.findByIdAndStatus(id, false);
        if (!taskOptional.isPresent()) {
            response.put("message", "No se encontró la tarea con ID " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        taskService.softDeleteById(id);
        response.put("message", "La tarea con ID " + id + " ha sido eliminado correctamente.");
        return ResponseEntity.ok(response);
    }
}
