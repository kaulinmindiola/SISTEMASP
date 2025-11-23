package com.application.psm.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="sector")
public class Sector {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable=false)
	private String name;
	
	@Column(name = "description", nullable=false)
	private String description;
	
	@Column(name = "url", nullable=false, columnDefinition = "TEXT")
	private String urlImg;
	
	//@OneToMany(mappedBy = "sector")
	//private List<JobOffer> jobs;
	
	public Sector() {
		super();
	}

	public Sector(String name, String description, String urlImg) {
		super();
		this.name = name;
		this.description = description;
		this.urlImg = urlImg;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrlImg() {
		return urlImg;
	}

	public void setUrlImg(String urlImg) {
		this.urlImg = urlImg;
	}
}
