package com.application.psm.service.imp;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.application.psm.model.JobOffer;
import com.application.psm.repository.JobOfferRepository;
import com.application.psm.service.JobOfferService;

@Service
public class JobOfferServiceImp implements JobOfferService {
	@Autowired
	private JobOfferRepository jobRepository;
	
	
	@Override
	public List<JobOffer> getAllJobOffer() {
		// TODO Auto-generated method stub
		return jobRepository.findAll();
	}

	@Override
	public JobOffer saveJobOffer(JobOffer job) {
		// TODO Auto-generated method stub
		return jobRepository.save(job);
	}

	@Override
	public void deleteJobOfferr(Long id) {
		// TODO Auto-generated method stub
		jobRepository.deleteById(id);
	}

	@Override
	public JobOffer editJobOffer(JobOffer job) {
		// TODO Auto-generated method stub
		return jobRepository.save(job);
	}

	@Override
	public JobOffer getJobOfferById(Long id) {
		// TODO Auto-generated method stub
		return jobRepository.findById(id).get();
	}

	@Override
	public boolean isJobOfferRelatedToPostulant(Long id) {
		// TODO Auto-generated method stub
		return jobRepository.existsById(id);
	}
}
