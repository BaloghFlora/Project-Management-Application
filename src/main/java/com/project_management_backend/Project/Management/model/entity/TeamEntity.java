package com.project_management_backend.Project.Management.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "teams")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "team_name", nullable = false)
    private String teamName;

    @Column(name = "description")
    private String description;
}