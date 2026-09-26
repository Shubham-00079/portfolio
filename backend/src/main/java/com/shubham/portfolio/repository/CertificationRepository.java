package com.shubham.portfolio.repository;

import com.shubham.portfolio.domain.Certification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificationRepository extends JpaRepository<Certification, Long> {
    List<Certification> findAllByOrderByIdDesc();
}
