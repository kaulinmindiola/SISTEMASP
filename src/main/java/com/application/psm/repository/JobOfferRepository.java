package com.application.psm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.psm.model.JobOffer;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer,Long>{

}
