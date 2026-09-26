package com.shubham.portfolio.repository;

import com.shubham.portfolio.domain.Experience;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    List<Experience> findAllByOrderByIdDesc();
}
