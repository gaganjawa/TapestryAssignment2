package com.psl.tapestry.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psl.tapestry.entities.User;

/**
 * Jpa Repository for CRUD oprerations.
 * @author gagan_jawa
 * @param <T>
 *
 */
public interface UserRepository extends JpaRepository<User, Integer>{
	
}
