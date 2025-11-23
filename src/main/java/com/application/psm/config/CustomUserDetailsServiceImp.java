package com.application.psm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.application.psm.model.User;
import com.application.psm.repository.UserRepository;



@Service
public class CustomUserDetailsServiceImp implements UserDetailsService{
		
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
        if (user != null) {
            // Obtener roles asociados al usuario (asumiendo que tienes un método en UserRepository para esto)
            //Collection<Role> roles = user.getRoles();
            return new CustomUserDetails(user);
        } else {
            throw new UsernameNotFoundException("User not found");
        }
	}
}
	
	

	
