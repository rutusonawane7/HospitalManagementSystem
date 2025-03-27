package com.example.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Entity.PatientAppointment;
import com.example.Entity.PatientRegistration;
import com.example.Entity.PatientTreatment;

public interface PatientTreatmentRepository extends JpaRepository<PatientTreatment, Long> {

	//List<PatientTreatment> findByAppointmentId(PatientAppointment patientAppointment);

	List<PatientTreatment> findByPatientAppointment(PatientAppointment appointment);

	List<PatientTreatment> findByPatientAppointmentPatient(PatientRegistration patient);



}
