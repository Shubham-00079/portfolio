package com.shubham.portfolio.repository;

import com.shubham.portfolio.domain.Skill;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findAllByOrderByIdAsc();
}
