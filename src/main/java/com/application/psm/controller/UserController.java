package com.application.psm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.application.psm.model.Postulant;
import com.application.psm.model.Sector;
import com.application.psm.model.User;
import com.application.psm.service.UserService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public UserController(UserService userService) {
	     this.userService = userService;
	}
	
     //============================ Metodo para mostrar form-login de inicio de sesion  ============================
	 @GetMapping("/login-user")
	 public String loginHome(Model model) {
	      // Lógica para mostrar la página de inicio de sesión
	      User user = new User();
	      user.setLoggedIn(false);
	      model.addAttribute("user", user);
	      return "login";
	  }
	 
	 //============================ Metodo para mostrar verficar credenciales ============================
	 @PostMapping("/login")
	 public String loginAccount(@ModelAttribute User user) {
	     // Lógica para verificar las credenciales y establecer el estado de inicio de sesión
	     user.setLoggedIn(true);
	     // Redirige a la página principal o a la página específica para usuarios logueados
	     return "redirect:/index";
	 }
	 
	//============================ Metodo para mostrar verficar credenciales al cerrar sesion ============================
	 @GetMapping("/logout")
		public String logoutAccount(@ModelAttribute User user) {
		       // Lógica para cerrar sesión y cambiar el estado de loggedIn
		        user.setLoggedIn(false);
		      return "redirect:/user/login-user";
	}
	 
	//============================ Metodo para mostrar form de registro de nuevo usuario ============================
	@GetMapping("/new-user")
	public String showUserForm(Model model) {
		model.addAttribute("user", new User()); // Asegúrate de que el objeto 'user' esté en el modelo
		return "form-user";
	}
	
	//============================ Metodo para registrar usuario ============================
	//@Secured("hasRole('ADMIN','VISITANTE')")
	@PostMapping("/registration-user")
	public String saveUserAccount(@ModelAttribute("user") User user, HttpSession session) {
		
		 boolean f = userService.checkUsername(user.getUsername());
		    // System.out.println(user);
		    if(f) {
		        
		        return "redirect:/user/new-user?error";
		    } else {
		        
		        User u = userService.saveUser(user);
		        
		        if (u != null) {
		            // System.out.println("save sucess");
		            session.setAttribute("success", "Register successfully");

		        } else {
		            // System.out.println("error in server");
		            session.setAttribute("error", "Something wrong server");
		        }
		    }
		    return "redirect:/user/new-user?success"; // Modificación en la URL de redirección
	}
	
	//============================ Metodo para modificar usuario ============================
	@PostMapping("/registration-user-update")
	public String updateUserAccount(@ModelAttribute("user") User user, HttpSession session, @AuthenticationPrincipal UserDetails userDetails, Model model) {
		boolean isLoggedIn = userDetails != null;
		model.addAttribute("isLoggedIn", isLoggedIn);	
		
	    //Obtener el usuario logueado seleccionado
		String username = userDetails.getUsername();
		model.addAttribute("username", username);

		 User existing= userService.findByUsername(username);

		 if (existing != null) {
			     // Sobrescribir solo los campos que se desean modificar
			     existing.setName(user.getName());
			     existing.setLastname(user.getLastname());
			     existing.setEmail(user.getEmail());
			     existing.setUsername(user.getUsername());
			   //existing.setPassword(user.getPassword());
			     userService.editUser(existing);
			        } 
			     return "redirect:/user/profile";
			}
			
	//============================ Metodo para editar perfil de usuario logueado ============================
	@GetMapping("/edit-profile/{id}")
	public String updateProfile(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
		if (userDetails != null) {
            // Verificar si el usuario tiene el rol de VISITANTE
            boolean isAdmin = userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_VISITANTE"));
            if (isAdmin) {
            	String username = userDetails.getUsername();
    	        model.addAttribute("username", username);
        	    boolean isLoggedIn = userDetails != null;
        	    User user = userService.findByUsername(username);
    	        model.addAttribute("user", user);
        	    model.addAttribute("isLoggedIn", isLoggedIn);
        	    return "user-edit";
            } else {
                // Si el usuario no es administrador, redireccionar a una página de error
                return "/layout/error/403";
            }
        } else {
            // Manejar el caso cuando el usuario no está autenticado
        	 return "redirect:/user/login-user";
        }
	}
	

	//============================ Metodo para eliminar perfil de usuario logueado ============================
	@GetMapping("/delete-profile/{id}")
	public String deleteProfile(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
		if (userDetails != null) {
            // Verificar si el usuario tiene el rol de visitante
            boolean isAdmin = userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_VISITANTE"));
            if (isAdmin) {
		            	// Verificar si el job está relacionado con un sector
		     		    userService.deleteUser(id);
		     		    return "redirect:/user/login-user";
            } else {
                // Si el usuario no es visitante, redireccionar a una página de error
                return "/layout/error/403";
            }
        } else {
            // Manejar el caso cuando el usuario no está autenticado
        	 return "redirect:/user/login-user";
        }	  
	}
	
	//============================ Metodo para mostrar form para cambiar password ============================
	@GetMapping("/form-password")
	public String showPasswordForm(Model model, @AuthenticationPrincipal UserDetails userDetails) {
		boolean isLoggedIn = userDetails != null;
		model.addAttribute("isLoggedIn", isLoggedIn);	
	      return "user-changing-password"; 
	}
	
	//============================ Metodo para update para cambiar password ============================
	@PostMapping("/update-password")
	public String updatePasswordForm(@RequestParam("oldPass") String oldPass, @RequestParam("newPass") String newPass, Model model,  @AuthenticationPrincipal UserDetails userDetails, HttpSession session) {
			boolean isLoggedIn = userDetails != null;
			model.addAttribute("isLoggedIn", isLoggedIn);
		
			String username = userDetails.getUsername();
		     // También agrega el objeto user al modelo si es necesario
		     User user = userService.findByUsername(username);
		     
		    boolean isPasswordMatch = passwordEncoder.matches(oldPass, user.getPassword());
	        
	        if (isPasswordMatch) {
	            user.setPassword(passwordEncoder.encode(newPass));
	            userService.editUser(user);
	            model.addAttribute("success", true);
	            return "user-changing-password";
	        } else {
	            // La contraseña antigua no coincide, manejar el error
	        	model.addAttribute("error", true);
	            return "user-changing-password";
	        }

	}
	
	
	//============================ Metodo para mostrar perfil de usuario logueado ============================
	@GetMapping("/profile")
	public String profileShow(Model model, @AuthenticationPrincipal UserDetails userDetails) {
		// Lógica para determinar si el usuario está logueado
	    boolean isLoggedIn = userDetails != null;

	    // Puedes agregar más lógica si es necesario
	    model.addAttribute("isLoggedIn", isLoggedIn);
	    // Obtén el nombre del usuario directamente desde userDetails
	    if (isLoggedIn) {
	        String username = userDetails.getUsername();
	        model.addAttribute("username", username);
	       
	        // También agrega el objeto user al modelo si es necesario
	        User user = userService.findByUsername(username);
	        model.addAttribute("user", user);
	    }
		return "user-profile";
	}
    
}
