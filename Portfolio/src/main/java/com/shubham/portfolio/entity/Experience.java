package com.shubham.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "experience")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String jobTitle;

    private String employmentType;

    private String location;

    private String startDate;

    private String endDate;

    private Boolean currentlyWorking;

    @Column(length = 3000)
    private String description;

    private Integer displayOrder;

    private Boolean active;
}