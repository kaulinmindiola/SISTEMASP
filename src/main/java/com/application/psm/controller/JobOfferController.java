package com.application.psm.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.application.psm.model.JobOffer;
import com.application.psm.model.Sector;
import com.application.psm.service.JobOfferService;
import com.application.psm.service.SectorService;

@Controller
@RequestMapping("/job")
public class JobOfferController {
	@Autowired
	private JobOfferService jobService;
	
	@Autowired
	private SectorService sectorService;
	
	//============================ Metodo para mostrar todos los jobs ============================
	@GetMapping("/all-jobs")
	public String jobOfferForm(Model model, @AuthenticationPrincipal UserDetails userDetails) {
		// Lógica para determinar si el usuario está logueado
	    boolean isLoggedIn = userDetails != null;
	    // Puedes agregar más lógica si es necesario
	    model.addAttribute("isLoggedIn", isLoggedIn);
	    model.addAttribute("sectors", sectorService.getAllSectors());
	    model.addAttribute("jobs", jobService.getAllJobOffer()); // Asegúrate de que el objeto 'user' esté en el modelo
		model.addAttribute("userDetails", userDetails); // Asegúrate de que el objeto 'userDetails' esté en el modelo
		return "publishing-job";
	}
	
	//============================ Metodo para mostrar formulario ============================
	@GetMapping("/new-job")
	public String jobShowForm(Model model, @AuthenticationPrincipal UserDetails userDetails) {
		if (userDetails != null) {
            // Verificar si el usuario tiene el rol de administrador
            boolean isAdmin = userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
            if (isAdmin) {
            	boolean isLoggedIn = userDetails != null;
        	    JobOffer job = new JobOffer();
        	    model.addAttribute("job", job);
        	    model.addAttribute("sectors", sectorService.getAllSectors());
        	    model.addAttribute("isLoggedIn", isLoggedIn);
        	    return "form-job";
            } else {
                // Si el usuario no es administrador, redireccionar a una página de error
                return "/layout/error/403";
            }
        } else {
            // Manejar el caso cuando el usuario no está autenticado
        	 return "redirect:/user/login-user";
        }
	}

	//============================ Metodo para registrar un job ============================
	@PostMapping("/registration-job")
	public String saveJobOffer(@ModelAttribute("sector") JobOffer job, @RequestParam("sectorId") Long sectorId) {
		if (job.getId() == null) {
			//sectorId es el valor que eligue el usuario en form tiene que tener campo id="sectorId" etc
			//Obtener el Sector seleccionado
		    Sector sector = sectorService.getSectorById(sectorId);
		    // Establecer el Sector en la JobOffer
		    job.setSector(sector);
	        // Crear un nuevo sector
			jobService.saveJobOffer(job);
	    } else {
	    	//Obtener el Sector seleccionado
		    Sector sector = sectorService.getSectorById(sectorId);
		    // Establecer el Sector en la JobOffer
		    job.setSector(sector);
	        // Editar un sector existente
	    	jobService.editJobOffer(job);
	    }
		return "redirect:/job/all-jobs";
	}
	//============================ Metodo para editar un sector ============================
	@GetMapping("/edit-job/{id}")
	public String editJob(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
		if (userDetails != null) {
            // Verificar si el usuario tiene el rol de administrador
            boolean isAdmin = userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
            if (isAdmin) {
            	   boolean isLoggedIn = userDetails != null;
	     		   JobOffer job = jobService.getJobOfferById(id);
	     		   model.addAttribute("job", job);
	     		   model.addAttribute("sectors", sectorService.getAllSectors());
	     		   model.addAttribute("isLoggedIn", isLoggedIn);
	     		   return "form-job";
            } else {
                // Si el usuario no es administrador, redireccionar a una página de error
                return "/layout/error/403";
            }
        } else {
            // Manejar el caso cuando el usuario no está autenticado
        	 return "redirect:/user/login-user";
        }
	}
		
	//============================ Metodo para eliminar un sector============================
	@GetMapping("/delete-job/{id}")
	public String deleteJob(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
		if (userDetails != null) {
            // Verificar si el usuario tiene el rol de administrador
            boolean isAdmin = userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
            if (isAdmin) {
		            	// Verificar si el job está relacionado con un sector
		         	   if (sectorService.isJobRelatedToSector(id)) {
		         	    	return "/layout/error/400";
		         	    }else {
		         	    	jobService.deleteJobOfferr(id);
		         			return "redirect:/job/all-jobs";
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
