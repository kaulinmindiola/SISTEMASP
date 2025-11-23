package com.application.psm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.application.psm.model.Role;
import com.application.psm.model.User;
import com.application.psm.service.RoleService;
import com.application.psm.service.UserService;

@Component
public class AdminUserInitializer implements ApplicationRunner {
	@Autowired 
    private UserService userService;
	
	@Autowired 
    private RoleService roleService;
	
	@Value("${ADMIN_NAME}")
    private String adminName;

    @Value("${ADMIN_LASTNAME}")
    private String adminLastname;

    @Value("${ADMIN_EMAIL}")
    private String adminEmail;

    @Value("${ADMIN_USERNAME}")
    private String adminUsername;

    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		try {
            if (!roleService.existsByName("ADMIN")) {
                Role adminRole = new Role("ADMIN",null);
                roleService.saveRole(adminRole);
            }

            if (!roleService.existsByName("VISITANTE")) {
                Role userRole = new Role("VISITANTE",null);
                roleService.saveRole(userRole);
            }

            if (!userService.existsAdminRole()) {
                User adminUser = new User();
                adminUser.setName(adminName);
                adminUser.setLastname(adminLastname);
                adminUser.setEmail(adminEmail);
                adminUser.setUsername(adminUsername);
                adminUser.setPassword(adminPassword);
                userService.saveUser(adminUser);
            }
        } catch (Exception e) {
            // Manejar cualquier excepción que pueda ocurrir durante la inicialización
            e.printStackTrace();
        }
        
	}
}
