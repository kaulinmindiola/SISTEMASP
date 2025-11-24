package com.application.psm.controller;

import org.springframework.http.MediaType;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
//import org.springframework.security.access.prepost.PreAuthorize;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.application.psm.model.JobOffer;
import com.application.psm.model.Postulant;
import com.application.psm.model.User;
import com.application.psm.service.JobOfferService;
import com.application.psm.service.PostulantService;
import com.application.psm.service.UserService;

@Controller
@RequestMapping("/postulant")
public class PostulantController {
	@Autowired 
	private JobOfferService jobService;
	@Autowired 
	private PostulantService postulantService;
	@Autowired 
	private UserService userService;
	
	//============================ Metodo para mostrar formulario ============================
	@GetMapping("/new-postulant")
	public String showPostulantForm(Model model, @AuthenticationPrincipal UserDetails userDetails, @RequestParam("jobId") Long jobId) {
		if (userDetails != null) {
            // Verificar si el usuario tiene el rol de visitante
            boolean isAdmin = userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_VISITANTE"));
            if (isAdmin) {
	            	 boolean isLoggedIn = userDetails != null;
	         	    // Obtener el Job seleccionado
	         	 	JobOffer job = jobService.getJobOfferById(jobId);
	         	    Postulant postulant = new Postulant();
	         	    model.addAttribute("job", job);
	         	    model.addAttribute("postulant", postulant);
	         	    model.addAttribute("isLoggedIn", isLoggedIn);
	         		return "form-postulant";
            } else {
                // Si el usuario no es visitante, redireccionar a una página de error
                return "/layout/error/403";
            }
        } else {
            // Manejar el caso cuando el usuario no está autenticado
        	 return "redirect:/user/login-user";
        }
	}
	//============================ Metodo para registrar postulante ============================
@PostMapping("/registration-postulant")
public String savePostulant(@ModelAttribute("postulant") Postulant postulant,
                            @RequestParam(value = "jobId", required = false) Long jobId,
                            @RequestParam(value = "pdfFile", required = false) MultipartFile pdfFile,
                            @AuthenticationPrincipal UserDetails userDetails) {

    try {
        // 1. Manejo del Archivo PDF
        if (pdfFile != null && !pdfFile.isEmpty()) {
            postulant.setCurriculum(pdfFile.getBytes());
            postulant.setPdfFileName(pdfFile.getOriginalFilename());
        }

        // 2. Asociar Usuario Logueado (Si existe)
        if (userDetails != null) {
            User user = userService.findByUsername(userDetails.getUsername());
            postulant.setUser(user);
        }

        // 3. LÓGICA PRINCIPAL (Crear o Editar)
        if (postulant.getId() == null) {
            // ES NUEVO REGISTRO
            if (jobId == null) {
                // Seguridad: Si no llega el ID del trabajo, no podemos guardar. Redirigimos.
                return "redirect:/job/all-jobs?error=missing-job";
            }
            JobOffer job = jobService.getJobOfferById(jobId);
            postulant.setJobOffer(job);
            postulantService.savePostulant(postulant);
        } else {
            // ES EDICIÓN
            Postulant existing = postulantService.getPostulantById(postulant.getId());
            if (existing != null) {
                existing.setAge(postulant.getAge());
                existing.setPhone(postulant.getPhone());
                existing.setStudies(postulant.getStudies());
                existing.setAddress(postulant.getAddress());
                existing.setAditional(postulant.getAditional());
                // Si subió un nuevo PDF, lo actualizamos
                if (pdfFile != null && !pdfFile.isEmpty()) {
                    existing.setCurriculum(pdfFile.getBytes());
                    existing.setPdfFileName(pdfFile.getOriginalFilename());
                }
                postulantService.editPostulant(existing);
            }
        }

        return "redirect:/postulant/user";

    } catch (Exception e) {
        e.printStackTrace();
        return "redirect:/job/all-jobs?error=upload-failed";
    }
}
	
	//============================ Metodo para editar un postulante ============================
	@GetMapping("/update-postulant/{id}")
	public String editPostulantUser(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
		
		if (userDetails != null) {
            // Verificar si el usuario tiene el rol de visitante
            boolean isAdmin = userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_VISITANTE"));
            if (isAdmin) {
	            	boolean isLoggedIn = userDetails != null;
	        		Postulant postulant = postulantService.getPostulantById(id);
	        		model.addAttribute("postulant", postulant);
	        		model.addAttribute("isLoggedIn", isLoggedIn);
	        		
	        		return "form-postulant";
            } else {
                // Si el usuario no es visitante, redireccionar a una página de error
                return "/layout/error/403";
            }
        } else {
            // Manejar el caso cuando el usuario no está autenticado
        	 return "redirect:/user/login-user";
        }	
	}
			
	//============================ Metodo para eliminar un postulante ============================
	@GetMapping("/delete-postulant/{id}")
	public String deletePostulantUser(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
		if (userDetails != null) {
            // Verificar si el usuario tiene el rol de visitante
            boolean isAdmin = userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_VISITANTE"));
            if (isAdmin) {
		            	// Verificar si el job está relacionado con un sector
		     		    postulantService.deletePostulant(id);
		     		    return "redirect:/postulant/user";
            } else {
                // Si el usuario no es visitante, redireccionar a una página de error
                return "/layout/error/403";
            }
        } else {
            // Manejar el caso cuando el usuario no está autenticado
        	 return "redirect:/user/login-user";
        }	  
	}
	
	//============================ Metodo para mostrar tabla de todos los postulantes ============================
	@Secured("hasRole('ADMIN')")
	@GetMapping("/admin")
	public String postulantShowTable(Model model, @AuthenticationPrincipal UserDetails userDetails) {
		if (userDetails != null) {
            // Verificar si el usuario tiene el rol de administrador
            boolean isAdmin = userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
            
            if (isAdmin) {
                String username = userDetails.getUsername();
                model.addAttribute("username", username);
                User user = userService.findByUsername(username);
                model.addAttribute("user", user);
                model.addAttribute("postulants", postulantService.getAllPostulants());
                boolean isLoggedIn = userDetails != null;
                model.addAttribute("isLoggedIn", isLoggedIn);
                return "admin-postulants";
            } else {
                // Si el usuario no es administrador, redireccionar a una página de error 404
                return "/layout/error/403";
            }
        } else {
            // Manejar el caso cuando el usuario no está autenticado
        	 return "redirect:/user/login-user";
        }
	}

	//============================ Metodo para mostrar tabla de todos los postulaciones del usuario expecifico ============================
	@Secured("hasRole('VISITANTE')")
	@GetMapping("/user")
	public String mypostulantsShow(Model model, @AuthenticationPrincipal UserDetails userDetails) {
		if (userDetails != null) {
            // Verificar si el usuario tiene el rol de visitante
            boolean isAdmin = userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_VISITANTE"));
            
            if (isAdmin) {
            	// Lógica para determinar si el usuario está logueado
        	    boolean isLoggedIn = userDetails != null;
        	    model.addAttribute("isLoggedIn", isLoggedIn);
        	  
        	    String username = userDetails.getUsername();
        	    User user = userService.findByUsername(username);
        	    
                Long userId = user.getId();
                List<Postulant> postulants = postulantService.getAllPostulantsByUserId(userId);
                model.addAttribute("postulants", postulants);
        	    
                return "user-postulants";
            } else {
                // Si el usuario es visitante, redireccionar a una página de error 404
                return "/layout/error/403";
            }
        } else {
            // Manejar el caso cuando el usuario no está autenticado
            return "redirect:/user/login-user";
        }
	}
	
	//============================ Metodo para mostrar descargar el pdf del usuario postulado============================
    @GetMapping("/download/{postulantId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Long postulantId) {
        // Obtener el postulante de la base de datos
        Postulant postulant = postulantService.getPostulantById(postulantId);
        if (postulant == null || postulant.getCurriculum() == null) {
            return ResponseEntity.notFound().build();
        }
        // Configurar los encabezados de la respuesta HTTP
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = MediaType.parseMediaType("application/pdf"); // Crear un MediaType para application/pdf
        headers.setContentType(mediaType); // Establecer el tipo de contenido
        headers.setContentDispositionFormData("filename", "curriculum.pdf");
        // Crear un recurso ByteArrayResource desde el contenido del PDF
        ByteArrayResource resource = new ByteArrayResource(postulant.getCurriculum());
        // Devolver el archivo PDF como una respuesta
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(postulant.getCurriculum().length)
                .body(resource);
    }
    //============================ Metodo para aceptar solicitud ============================
    @PostMapping("/{postulantId}/accept")
    public String acceptPostulant(@PathVariable("postulantId") Long postulantId) {
        // Lógica para aceptar la postulación
        Postulant postulant = postulantService.getPostulantById(postulantId);
        postulant.setEstado("aceptado");
        postulantService.savePostulant(postulant);
        return "redirect:/postulant/admin";
    }
    //============================ Metodo para rechazar solicitud ============================
    @PostMapping("/{postulantId}/reject")
    public String rejectPostulant(@PathVariable("postulantId") Long postulantId) {
        // Lógica para rechazar la postulación
        Postulant postulant = postulantService.getPostulantById(postulantId);
        postulant.setEstado("rechazado");
        postulantService.savePostulant(postulant);
        return "redirect:/postulant/admin";
    }
    
    
}
    