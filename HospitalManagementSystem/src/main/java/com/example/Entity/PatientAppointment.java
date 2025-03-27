package com.example.Entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class PatientAppointment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	private LocalDate appoinmentDate;
	private String remark;
	

	@ManyToOne
    @JoinColumn(name = "patient_id", nullable = false) 
    private PatientRegistration patient;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public LocalDate getAppoinmentDate() {
		return appoinmentDate;
	}


	public void setAppoinmentDate(LocalDate appoinmentDate) {
		this.appoinmentDate = appoinmentDate;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public PatientRegistration getPatient() {
		return patient;
	}


	public void setPatient(PatientRegistration patient) {
		this.patient = patient;
	}
	
	

}
