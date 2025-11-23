package com.application.psm.model;


import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name="role")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Long id;
	private String roleName;
	
	@OneToMany(mappedBy = "role")
    private List<User> users;
	
	public Role() {
	}

	public Role(String roleName, List<User> users) {
		super();
		this.roleName = roleName;
		this.users = users;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	
	
	
}