package com.application.psm.model;

import jakarta.persistence.*;

@Entity
@Table(name="postulant")
public class Postulant {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "age")
	private Integer age;
	@Column(name = "phone", nullable=false)
	private String phone;
	@Column(name = "studies")
	private String studies;
	@Column(name = "address")
	private String address;

	@Column(name = "curriculum", length = 4000000) // Cambia 4000000 al valor máximo que desees
	@Lob
    private byte[] curriculum;
    
    @Column(name = "pdf_file_name")
    private String pdfFileName;
    
    @Column(name = "aditional")
	private String aditional;
    
    @Column(name = "estado")
    private String estado; // Puede ser "aceptado", "rechazado", "pendiente", etc.
    
    @ManyToOne
   	@JoinColumn(name = "user_id")
   	private User user;
   	
   	@ManyToOne
      @JoinColumn(name = "job_offer_id")
   	private JobOffer jobOffer;
   	
    
	public Postulant() {
		this.estado = "pendiente";
	}


	public Postulant(Integer age, String phone, String studies, String address, byte[] curriculum, String pdfFileName,
			String aditional, String estado, User user, JobOffer jobOffer) {
		super();
		this.age = age;
		this.phone = phone;
		this.studies = studies;
		this.address = address;
		this.curriculum = curriculum;
		this.pdfFileName = pdfFileName;
		this.aditional = aditional;
		this.estado = estado;
		this.user = user;
		this.jobOffer = jobOffer;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Integer getAge() {
		return age;
	}


	public void setAge(Integer age) {
		this.age = age;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getStudies() {
		return studies;
	}


	public void setStudies(String studies) {
		this.studies = studies;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public byte[] getCurriculum() {
		return curriculum;
	}


	public void setCurriculum(byte[] curriculum) {
		this.curriculum = curriculum;
	}


	public String getPdfFileName() {
		return pdfFileName;
	}


	public void setPdfFileName(String pdfFileName) {
		this.pdfFileName = pdfFileName;
	}


	public String getAditional() {
		return aditional;
	}


	public void setAditional(String aditional) {
		this.aditional = aditional;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public JobOffer getJobOffer() {
		return jobOffer;
	}


	public void setJobOffer(JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
}
