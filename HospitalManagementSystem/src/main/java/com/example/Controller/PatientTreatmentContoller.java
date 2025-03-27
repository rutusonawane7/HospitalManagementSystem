package com.example.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.Entity.PatientTreatment;
import com.example.Service.PatientTreatmentService;

@RestController
public class PatientTreatmentContoller {
	
	
	@Autowired
	PatientTreatmentService treatmentService;
	
	@PostMapping("/saveTreatment/{appointment_id}")
	public ResponseEntity<PatientTreatment> addTreatment(
			@PathVariable Long appointment_id, 
			@RequestParam String doctorName,
			@RequestPart MultipartFile prescriptionPath){
		
		
		
		PatientTreatment treatment = new PatientTreatment();
		treatment.setDoctorName(doctorName);
		
		treatmentService.addTreatment(appointment_id,treatment,prescriptionPath);
		return ResponseEntity.status(HttpStatus.CREATED).body(treatment);
		
	}

	@GetMapping("/getPrescription/{appointment_id}")
	public ResponseEntity<Resource> getTreatmentByAppointmentId(@PathVariable Long appointment_id ){
		Resource treatmentPhoto = treatmentService.getTreatmentByAppointmentId(appointment_id);
		
		if(treatmentPhoto != null) {
			return ResponseEntity.ok()
					.contentType(MediaType.IMAGE_JPEG)
					.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + treatmentPhoto.getFilename()+ "\"")
					.body(treatmentPhoto);
			
		}else {
			return ResponseEntity.notFound().build();
		}

	}
	
	@GetMapping("/getPrescriptionByPatientId/{patient_id}")
	public ResponseEntity<Resource> getTreatmentPerscriptionByPatientId(@PathVariable Long patient_id ){
		Resource treatmentPhoto = treatmentService.getTreatmentPerscriptionByPatientId(patient_id);
		
		if(treatmentPhoto != null) {
			return ResponseEntity.ok()
					.contentType(MediaType.IMAGE_JPEG)
					.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + treatmentPhoto.getFilename()+ "\"")
					.body(treatmentPhoto);
			
		}else {
			return ResponseEntity.notFound().build();
		}

	}
	
	

}
