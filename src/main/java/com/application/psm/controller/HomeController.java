package com.application.psm.controller;


import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.application.psm.model.JobOffer;
import com.application.psm.model.Postulant;
import com.application.psm.model.Sector;

@Controller
@RequestMapping("/home")
public class HomeController {
	
	
	//============================ Metodo para mostrar crear una lista por defecto ============================
	public List<JobOffer> getPostsTop(){
		ArrayList<JobOffer> post = new ArrayList<>();
		
		// Crear un objeto Sector si es necesario
	    Sector sector = null; // Por ahora lo dejamos como null, puedes asignar un objeto Sector válido si lo tienes
	    
	    // Crear una lista de postulantes si es necesario
	    List<Postulant> postulants = null; // Por ahora lo dejamos como null, puedes asignar una lista válida de postulantes si lo tienes

		post.add(new JobOffer("Programacion OOP", "Se necesita top1", "que sepa ingles", "que sepa fraaces", "que sepa chino","https://assets.ey.com/content/dam/ey-sites/ey-com/es_pe/topics/forensic/ey-siete-sugerencias-proceso-analisis-de-datos.jpg",sector,postulants));
		post.add(new JobOffer("Ing. Electronico", "Se necesita top2", "que sepa ingles", "que sepa fraaces", "que sepa chino","https://assets.ey.com/content/dam/ey-sites/ey-com/es_pe/topics/forensic/ey-siete-sugerencias-proceso-analisis-de-datos.jpg",sector,postulants));
	    post.add(new JobOffer("Developer Kotlin", "Se necesita top3", "que sepa ingles", "que sepa fraaces", "que sepa chino","https://assets.ey.com/content/dam/ey-sites/ey-com/es_pe/topics/forensic/ey-siete-sugerencias-proceso-analisis-de-datos.jpg",sector,postulants));
		return post;
	}
	
	//============================ Metodo para mostrar home principal de la pagina ============================
	@GetMapping
	public String showHome(Model model, @AuthenticationPrincipal UserDetails userDetails) {
	    // Lógica para determinar si el usuario está logueado
	    boolean isLoggedIn = userDetails != null;

	    // Puedes agregar más lógica si es necesario

	    model.addAttribute("isLoggedIn", isLoggedIn);
	    
	    model.addAttribute("jobs", this.getPostsTop()); // Asegúrate de que el objeto 'user' esté en el modelo

	    return "index";
	}
	
	
	
	

}	
