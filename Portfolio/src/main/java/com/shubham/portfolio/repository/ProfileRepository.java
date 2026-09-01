package com.shubham.portfolio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shubham.portfolio.entity.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
	   Optional<Profile> findTopByOrderByIdAsc();
}