package com.shubham.portfolio.repository;

import com.shubham.portfolio.domain.Education;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationRepository extends JpaRepository<Education, Long> {
    List<Education> findAllByOrderByIdDesc();
}
