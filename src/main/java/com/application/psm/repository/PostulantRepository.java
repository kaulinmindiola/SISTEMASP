package com.application.psm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.psm.model.Postulant;

@Repository
public interface PostulantRepository extends JpaRepository<Postulant,Long>{
	 //List<Postulant> findPostulantByUserId(Long userId);
}
