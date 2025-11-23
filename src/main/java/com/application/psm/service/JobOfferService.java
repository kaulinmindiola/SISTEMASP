package com.application.psm.service;

import java.util.List;

import com.application.psm.model.JobOffer;

public interface JobOfferService {

public List<JobOffer> getAllJobOffer();
	
	public JobOffer saveJobOffer(JobOffer job);
	
	public void deleteJobOfferr(Long id);
	
	public JobOffer editJobOffer(JobOffer job);
	
	public JobOffer getJobOfferById(Long id);
	
	public boolean isJobOfferRelatedToPostulant(Long id);
}
