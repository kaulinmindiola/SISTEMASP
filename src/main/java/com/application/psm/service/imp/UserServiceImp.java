package com.application.psm.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.application.psm.model.Role;
import com.application.psm.model.User;
import com.application.psm.repository.RoleRepository;
import com.application.psm.repository.UserRepository;
import com.application.psm.service.UserService;

@Service
public class UserServiceImp implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;


	@Override
	public User saveUser(User user) {
		
		boolean adminExists = userRepository.existsAdminRole();
		System.out.println("¿Existe un administrador en la base de datos?: " + adminExists);


		if (adminExists) {
		    // Ya se ha agregado un administrador a la base de datos
			Role userRole = roleRepository.findById(2L).get();

			User newUser = new User(
				    user.getName(),
				    user.getLastname(),
				    user.getEmail(),
				    user.getUsername(),
				    passwordEncoder.encode(user.getPassword()),
				    userRole
				);
			newUser.setLoggedIn(false);
			return userRepository.save(newUser);
			
			} else {
		    // No se ha agregado ningún administrador a la base de datos aún
			Role adminRole = roleRepository.findById(1L).get(); // Buscar el rol de administrador por su nombre
		    if (adminRole == null) {
		        // Manejar el caso en el que el rol de administrador no existe en la base de datos
		        throw new RuntimeException("Admin role not found in the database");
		    }

		    // Crear el usuario con el rol de administrador
		    User adminUser = new User(
		            user.getName(),
		            user.getLastname(),
		            user.getEmail(),
		            user.getUsername(),
		            passwordEncoder.encode(user.getPassword()),
		            adminRole // Asignar el rol de administrador
		    );
		    adminUser.setLoggedIn(false);
			return userRepository.save(adminUser);
		}
	}


	@Override
	public boolean checkUsername(String username) {
		// TODO Auto-generated method stub
		return userRepository.existsByUsername(username);
	}


	@Override
	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(username);
	}

	@Override
	public String getUserRole(String username) {
		User user = userRepository.findByUsername(username);
        if (user != null) {
            Long roleId = user.getRoles().getId();
            Role role = roleRepository.findById(roleId).orElse(null);
            if (role != null) {
                return role.getRoleName();
            }
        }
        return null;
	}

	@Override
	public boolean existsAdminRole() {
		// TODO Auto-generated method stub
		return userRepository.existsAdminRole();
	}


	@Override
	public void deleteUser(Long id) {
		// TODO Auto-generated method stub
		userRepository.deleteById(id);
	}


	@Override
	public User editUser(User user) {
		// TODO Auto-generated method stub
		//Role userRole = roleRepository.findById(2L).get();
		/*User userUpdate = new User(
			    user.getName(),
			    user.getLastname(),
			    user.getEmail(),
			    user.getUsername(),
			    passwordEncoder.encode(user.getPassword()),
			    userRole
			);
		userUpdate.setLoggedIn(true);*/
		return userRepository.save(user);
	}
}


	
	