package com.example.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Entity.PatientRegistration;
import com.example.Service.PatientRegistrationService;

@RestController
public class PatientRegistrationController {
	
	@Autowired
	PatientRegistrationService patientService;
	
	
	@PostMapping("/register/newPatient")
	public ResponseEntity<String> registerPatient(@RequestBody PatientRegistration newPatient) 
	{
		 
		String result = patientService.registerPatient(newPatient);
        if (result.contains("already exists")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
		
		
	}
	@GetMapping("/retrieve/patientDetails/{id}")
	public PatientRegistration getPatientDetailsById(@PathVariable Long id) {
		PatientRegistration patient = patientService.getPatientDetailsById(id);
		return(patient);
	}

	@PutMapping("update/patientDetails/{id}")
	public ResponseEntity<?> updatePatientDetails(@PathVariable Long id, @RequestBody PatientRegistration updatePatient) {
		return patientService.updatePatientDetails(id,updatePatient);
		
	}
	
	@DeleteMapping("/delete/patientDetails/{id}")
	public String deletePatientDetailsById(@PathVariable Long id){
		return patientService.deletePatientDetailsById(id);
		
	}
	@GetMapping("/getAllRegisteredPatient")
	public ResponseEntity<List<PatientRegistration>> getAllRegisteredPatient() {
        List<PatientRegistration> patient = patientService.getAllRegisteredPatient();
        return ResponseEntity.ok(patient);
    }
	
	




}
