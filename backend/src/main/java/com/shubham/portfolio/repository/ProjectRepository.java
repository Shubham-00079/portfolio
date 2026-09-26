package com.shubham.portfolio.repository;

import com.shubham.portfolio.domain.Project;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByOrderByIdDesc();
    List<Project> findAllByFeaturedTrueOrderByIdDesc();
}
