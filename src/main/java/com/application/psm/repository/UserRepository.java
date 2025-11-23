package com.application.psm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.application.psm.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
	
	public User findByUsername(String username);
	public boolean existsByUsername(String username);
	
	@Query("SELECT COUNT(u) > 0 FROM User u WHERE u.role.roleName = 'ADMIN'")
	public boolean existsAdminRole();

}
