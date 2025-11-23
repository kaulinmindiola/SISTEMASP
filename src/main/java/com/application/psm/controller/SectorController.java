package com.application.psm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.application.psm.model.Sector;
import com.application.psm.service.SectorService;

@Controller
@RequestMapping("/sector")
public class SectorController {

	@Autowired
	private SectorService sectorService;
	
	//============================ Metodo para mostrar todos los sectors ============================
	@GetMapping("/all-sectors")
	public String sectorShowAll(Model model, @AuthenticationPrincipal UserDetails userDetails) {
		// Lógica para determinar si el usuario está logueado
	    boolean isLoggedIn = userDetails != null;
	    // Puedes agregar más lógica si es necesario
	    model.addAttribute("sectors", sectorService.getAllSectors()); // Asegúrate de que el objeto 'user' esté en el modelo
	    model.addAttribute("isLoggedIn", isLoggedIn);
		return "publishing-sector";
	}
	
	//============================ Metodo para mostrar formulario ============================
	@Secured("hasRole('ADMIN')")
	@GetMapping("/new-sector")
	public String sectorShowForm(Model model, @AuthenticationPrincipal UserDetails userDetails) {
	    if (userDetails != null) {
            // Verificar si el usuario tiene el rol de administrador
            boolean isAdmin = userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
            
            if (isAdmin) {
            	boolean isLoggedIn = userDetails != null;
        	    Sector sector = new Sector();
        	    model.addAttribute("sector", sector);
        	    model.addAttribute("isLoggedIn", isLoggedIn);
        	    return "form-sector";
            } else {
                // Si el usuario no es administrador, redireccionar a una página de error
                return "/layout/error/403";
            }
        } else {
            // Manejar el caso cuando el usuario no está autenticado
        	 return "redirect:/user/login-user";
        }

	}
	
	//============================ Metodo para registrar un sector ============================
	@PostMapping("/registration-sector")
	public String saveSector(@ModelAttribute("sector") Sector sector) {
		if (sector.getId() == null) {
	        // Crear un nuevo sector
	        sectorService.saveSector(sector);
	    } else {
	        // Editar un sector existente
	        sectorService.editSector(sector);
	    }
		return "redirect:/sector/all-sectors";
	}
	
	//============================ Metodo para editar un sector ============================
	@GetMapping("/edit-sector/{id}")
	public String editSector(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
		  if (userDetails != null) {
	            // Verificar si el usuario tiene el rol de administrador
	            boolean isAdmin = userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
	            if (isAdmin) {
	        	    boolean isLoggedIn = userDetails != null;
	        	    Sector sector = sectorService.getSectorById(id);
	        	    model.addAttribute("sector", sector);
	        	    model.addAttribute("isLoggedIn", isLoggedIn);
	        	    return "form-sector";
	            } else {
	                // Si el usuario no es administrador, redireccionar a una página de error
	                return "/layout/error/403";
	            }
	        } else {
	            // Manejar el caso cuando el usuario no está autenticado
	        	 return "redirect:/user/login-user";
	        }
	}
	
	//============================ Metodo para eliminar un sector ============================
	@GetMapping("/delete-sector/{id}")
	public String deleteSector(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
		   if (userDetails != null) {
	            // Verificar si el usuario tiene el rol de administrador
	            boolean isAdmin = userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
	            if (isAdmin) {
	            	// Verificar si el job está relacionado con un sector
	     		   if (sectorService.isJobRelatedToSector(id)) {
	     		    	return "/layout/error/400";
	     		    }else {
	     				sectorService.deleteSector(id);
	     				return "redirect:/sector/all-sectors";
	     		    }
	            } else {
	                // Si el usuario no es administrador, redireccionar a una página de error
	                return "/layout/error/403";
	            }
	        } else {
	            // Manejar el caso cuando el usuario no está autenticado
	        	 return "redirect:/user/login-user";
	        }  
		
	}
	
	
}
