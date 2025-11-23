package com.application.psm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.psm.model.Sector;

@Repository
public interface SectorRepository extends JpaRepository<Sector,Long>{

}
