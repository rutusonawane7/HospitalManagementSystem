package com.example.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.Entity.PatientRegistration;
import com.example.Repository.PatientRegistrationRepository;

@Service
public class PatientRegistrationService {
	
	@Autowired
	PatientRegistrationRepository patientRepository;

	public String registerPatient(PatientRegistration newPatient) {
		
		if(patientRepository.existsByMobileNumber(newPatient.getMobileNumber())) {
			return "The Patient with this Mobile Number is Already Exist";
		}
		patientRepository.save(newPatient);	
		return "Patient save Successfully";
	}

	public PatientRegistration getPatientDetailsById(Long id) {
	
		Optional<PatientRegistration> patient = patientRepository.findById(id);
	    
	    if (patient.isPresent()) {
	        return patient.get();
	    } else {
	        throw new IllegalArgumentException("Patient not found with ID: " + id);
	    }
	


	}

	public ResponseEntity<?> updatePatientDetails(Long id, PatientRegistration updatePatient) {
		
		
		if (patientRepository.existsByMobileNumber(updatePatient.getMobileNumber())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The Patient with this Mobile Number Already Exists");
        }
	
		PatientRegistration patient = patientRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Patient not found with ID: " + id));

		patient.setFirstName(updatePatient.getFirstName());
		patient.setLastName(updatePatient.getLastName());
		patient.setAge(updatePatient.getAge());
		patient.setDateOfBirth(updatePatient.getDateOfBirth());
		patient.setMobileNumber(updatePatient.getMobileNumber());



		PatientRegistration updatedPatient = patientRepository.save(patient);
        
        return ResponseEntity.ok(updatedPatient);
	}

	public String deletePatientDetailsById(Long id) {
		if(patientRepository.existsById(id)) {
			patientRepository.deleteById(id);
			return "Patient Deleted Successfully";
		}
		return "Patient with id "+ id + " is not Present in the Database";
		

	}

	public List<PatientRegistration> getAllRegisteredPatient() {
		return patientRepository.findAll();
	}

	
}


