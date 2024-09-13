package com.crud.project_planner.controllers;

import com.crud.project_planner.models.dto.ProjectDTO;
import com.crud.project_planner.models.entities.Project;
import com.crud.project_planner.models.request.WordRequest;
import com.crud.project_planner.services.interfaces.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@RestController
@RequestMapping("/api/project")
public class ProjectController {
    @Autowired
    private IProjectService projectService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Project> projectOptional = projectService.findByIdAndStatus(id, false);
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            ProjectDTO projectDTO = ProjectDTO.builder()
                    .id(project.getId())
                    .name(project.getName())
                    .description(project.getDescription())
                    .status(project.getStatus())
                    .build();
            return ResponseEntity.ok(projectDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap("message", "Proyecto no encontrado"));
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
        List<Project> projects = projectService.findByNameContaining(word);
        if (projects.isEmpty()) {
            response.put("message", "No se encontraron proyectos con la palabra.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        List<ProjectDTO> projectDTOList = projects.stream()
                .map(project -> ProjectDTO.builder()
                        .id(project.getId())
                        .name(project.getName())
                        .description(project.getDescription())
                        .status(project.getStatus())
                        .build())
                .toList();

        return ResponseEntity.ok(projectDTOList);
    }
    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        List<ProjectDTO> projectDTOList = projectService.findByStatus(false).stream()
                .map(project -> ProjectDTO.builder()
                        .id(project.getId())
                        .name(project.getName())
                        .description(project.getDescription())
                        .status(project.getStatus())
                        .build()
                ).toList();
        return ResponseEntity.ok(projectDTOList);
    }
    @PostMapping("/save")
    public ResponseEntity<?> save( @RequestBody ProjectDTO projectDTO ) throws URISyntaxException {
        Map<String, String> messageResponse = new HashMap<>();
        if (projectDTO.getName() == null || projectDTO.getName().isBlank()) {
            messageResponse.put("message", "El campo 'name' es obligatorio y no puede estar vacío.");
            return ResponseEntity.badRequest().body(messageResponse);
        }
        Project project = Project.builder()
                .name(projectDTO.getName())
                .description(projectDTO.getDescription())
                .status(false)
                .build();
        projectService.save(project);
        messageResponse.put("message", "Proyecto creado correctamente.");
        return ResponseEntity.created(new URI("/api/project/save")).body(messageResponse);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProject(@PathVariable Long id, @RequestBody ProjectDTO projectDTO) {
        Map<String, String> messageResponse = new HashMap<>();
        if (projectDTO.getName() == null || projectDTO.getName().isBlank()) {
            messageResponse.put("message", "El campo 'name' es obligatorio y no puede estar vacío.");
            return ResponseEntity.badRequest().body(messageResponse);
        }
        Optional<Project> projectOptional = projectService.findByIdAndStatus(id, false);
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            project.setName(projectDTO.getName());
            if (projectDTO.getDescription() != null) {
                project.setDescription(projectDTO.getDescription());
            }
            project.setStatus(false);
            projectService.save(project);

            messageResponse.put("message", "Se Actualizo correctamente.");
            return ResponseEntity.ok().body(messageResponse);
        }
        messageResponse.put("message", "No se encontro el Id.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(messageResponse);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        if ( id == null ) {
            response.put("message", "El ID no puede ser nulo.");
            return ResponseEntity.badRequest().body(response);
        }
        Optional<Project> projectOptional = projectService.findByIdAndStatus(id, false);
        if (!projectOptional.isPresent()) {
            response.put("message", "No se encontró el proyecto con ID " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        projectService.softDeleteById(id);
        response.put("message", "El proyecto con ID " + id + " ha sido eliminado correctamente.");
        return ResponseEntity.ok(response);
    }
}
