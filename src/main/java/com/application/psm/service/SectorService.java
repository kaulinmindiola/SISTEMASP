package com.application.psm.service;

import java.util.List;

import com.application.psm.model.Sector;

public interface SectorService {
	public List<Sector> getAllSectors();
	
	public Sector saveSector(Sector sector);
	
	public void deleteSector(Long id);
	
	public Sector editSector(Sector sector);
	
	public Sector getSectorById(Long id);
	
	public boolean isJobRelatedToSector(Long id);

}
