package com.application.psm.service;

import java.util.List;

import com.application.psm.model.Postulant;

public interface PostulantService {
	
	public List<Postulant> getAllPostulants();
	
	public Postulant savePostulant(Postulant postulant);
	
	public void deletePostulant(Long id);
	
	public Postulant editPostulant(Postulant postulant);
	
	public Postulant getPostulantById(Long id);
		
	public boolean checkPostulant(String name);
	
	public List<Postulant> getAllPostulantsByUserId (Long id);
	
	//public String getUserRole (String username);
	
}
