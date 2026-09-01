package com.shubham.portfolio.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    private String designation;

    @Column(length = 2000)
    private String about;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    private String location;

    private String profileImage;

    private String resumeUrl;

    private String github;

    private String linkedin;
}