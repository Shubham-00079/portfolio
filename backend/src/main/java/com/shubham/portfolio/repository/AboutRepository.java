package com.shubham.portfolio.repository;

import com.shubham.portfolio.domain.About;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AboutRepository extends JpaRepository<About, Long> {
    Optional<About> findFirstByOrderByIdAsc();
}
