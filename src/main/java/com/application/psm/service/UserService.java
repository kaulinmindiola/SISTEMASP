package com.application.psm.service;

import com.application.psm.model.User;


public interface UserService{
	
	public User saveUser(User user);
	
	public void deleteUser(Long id);
	
	public User editUser(User user);
	
	public boolean checkUsername(String username);
	
	public User findByUsername (String username);
	
	public String getUserRole (String username);
	
	public boolean existsAdminRole();
	
	//List<User> getAllUsers();
	
	//void removeSessionMessage();
	
	
	
	//User getUserById(Long id);
	
}
