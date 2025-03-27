package com.example.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class PatientTreatment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String doctorName;
    private String prescription;
    
    
    public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getDoctorName() {
		return doctorName;
	}


	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}


	public String getPrescription() {
		return prescription;
	}


	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}


	

	public PatientAppointment getPatientAppointment() {
		return patientAppointment;
	}


	public void setPatientAppointment(PatientAppointment patientAppointment) {
		this.patientAppointment = patientAppointment;
	}


	@ManyToOne //PatientTreatment records can be associated with a single PatientAppointment
    @JoinColumn(name = "appointment_id", nullable = false)
    private PatientAppointment patientAppointment; 
	
	@ManyToOne // many treatment can be associated with single PatientRegistration 
    @JoinColumn(name = "patient_id", nullable = false)  // Foreign key to Patient
    private PatientRegistration patient;


}
