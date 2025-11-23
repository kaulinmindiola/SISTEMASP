package com.application.psm.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="joboffer")
public class JobOffer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "title", nullable=false)
	private String title;
	@Column(name = "description", nullable=false)
	private String description;
	@Column(name = "req1", nullable=false)
	private String requirements1;
	@Column(name = "req2", nullable=false)
	private String requirements2;
	@Column(name = "req3", nullable=false)
	private String requirements3;
	@Column(name = "url", nullable=false, columnDefinition = "TEXT")
	private String urlImg;
	
	@ManyToOne
    @JoinColumn(name = "sector_id")
	private Sector sector;
	
	@OneToMany(mappedBy = "jobOffer") // Nombre del atributo en la clase Postulant que mapea esta relación
    private List<Postulant> postulants; // Relación One-to-Many con la clase Postulant

	public JobOffer() {
		super();
	}

	public JobOffer(String title, String description, String requirements1, String requirements2, String requirements3,
			String urlImg, Sector sector, List<Postulant> postulants) {
		super();
		this.title = title;
		this.description = description;
		this.requirements1 = requirements1;
		this.requirements2 = requirements2;
		this.requirements3 = requirements3;
		this.urlImg = urlImg;
		this.sector = sector;
		this.postulants = postulants;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRequirements1() {
		return requirements1;
	}

	public void setRequirements1(String requirements1) {
		this.requirements1 = requirements1;
	}

	public String getRequirements2() {
		return requirements2;
	}

	public void setRequirements2(String requirements2) {
		this.requirements2 = requirements2;
	}

	public String getRequirements3() {
		return requirements3;
	}

	public void setRequirements3(String requirements3) {
		this.requirements3 = requirements3;
	}

	public String getUrlImg() {
		return urlImg;
	}

	public void setUrlImg(String urlImg) {
		this.urlImg = urlImg;
	}

	public Sector getSector() {
		return sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}

	public List<Postulant> getPostulants() {
		return postulants;
	}

	public void setPostulants(List<Postulant> postulants) {
		this.postulants = postulants;
	}

	
	
}