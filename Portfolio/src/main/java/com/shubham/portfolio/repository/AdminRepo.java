package com.shubham.portfolio.repository;

//package com.shubham.portfolio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shubham.portfolio.entity.Admin;

public interface AdminRepo extends JpaRepository<Admin, Long> {

    Optional<Admin> findByEmail(String email);

}