package com.example.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Entity.PatientAppointment;

public interface PatientAppointmentRepository extends JpaRepository<PatientAppointment, Long>{

	Optional<PatientAppointment> findByPatientId(Long patient_id);


}
