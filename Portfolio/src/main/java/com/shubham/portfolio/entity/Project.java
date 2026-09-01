package com.shubham.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 3000)
    private String description;

    private String technologies;

    private String githubUrl;

    private String liveDemoUrl;

    private String imageUrl;

    private Boolean featured;

    private Integer displayOrder;

    private Boolean active;
}