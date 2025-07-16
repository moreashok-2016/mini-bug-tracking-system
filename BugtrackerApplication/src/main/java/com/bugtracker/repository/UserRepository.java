package com.bugtracker.repository;

import com.bugtracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	
	boolean existsByEmail(String email);

}