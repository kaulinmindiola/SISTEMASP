package com.application.psm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.psm.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
	 Role findByRoleName(String roleName);
}
