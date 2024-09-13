package com.crud.project_planner.models.dto;

import com.crud.project_planner.models.entities.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDTO {

    private Long id;

    private String name;

    private LocalDate date;

    private Boolean status = false;

    private Project project;
}
