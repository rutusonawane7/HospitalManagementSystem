package com.example.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Entity.PatientAppointment;
import com.example.Entity.PatientRegistration;
import com.example.Repository.PatientAppointmentRepository;
import com.example.Repository.PatientRegistrationRepository;

@Service
public class PatientAppointmentService {

	@Autowired
	PatientAppointmentRepository appointmentRepository;
	
	@Autowired
	PatientRegistrationRepository patientRegistrationRepository;
	
	public PatientAppointment scheduleAppointment(Long patient_id, PatientAppointment appointment) {
		Optional<PatientRegistration> patientOptional = patientRegistrationRepository.findById(patient_id);

        if (patientOptional.isPresent()) {
            
            PatientRegistration patient = patientOptional.get();
            appointment.setPatient(patient);

            return appointmentRepository.save(appointment);
        } else {
            throw new RuntimeException("Patient not found with ID: " + patient_id);
        }
	}

	public PatientAppointment getAppointmentById(Long patient_id) {
		Optional<PatientAppointment> appointmentOptional = appointmentRepository.findByPatientId(patient_id);

        if (appointmentOptional.isPresent()) {
            return appointmentOptional.get();
        } else {
            throw new RuntimeException("Appointment not found for patient with ID: " + patient_id);
        }
    }

	public String cancleAppointment(Long id) {
		if(appointmentRepository.existsById(id)) {
			appointmentRepository.deleteById(id);
			return "Appointment Cancle Successfully";
		}
		return "Appointment with id "+ id + " is not Present in the Database";
		
	}

	public List<PatientAppointment> getAllAppointments() {
		return appointmentRepository.findAll();
	}

	public String updateStudent(Long patient_id, PatientAppointment appointment) {
		Optional<PatientAppointment> patientOptional = appointmentRepository.findById(patient_id);

        if (patientOptional.isEmpty()) {
	        throw new RuntimeException("Patient not found with ID: " + patient_id);

        }
        PatientAppointment existingAppointment = patientOptional.get();
        
        if (appointment.getPatient() != null) {
            existingAppointment.setPatient(appointment.getPatient()); 
        }

        existingAppointment.setAppoinmentDate(appointment.getAppoinmentDate());
        existingAppointment.setRemark(appointment.getRemark());

        appointmentRepository.save(existingAppointment);

        return "Appointment updated successfully for patient ID: " + patient_id;
    }
		
	
		 
}

