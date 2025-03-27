package com.example.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.Entity.PatientAppointment;
import com.example.Entity.PatientRegistration;
import com.example.Entity.PatientTreatment;
import com.example.Repository.PatientAppointmentRepository;
import com.example.Repository.PatientRegistrationRepository;
import com.example.Repository.PatientTreatmentRepository;

@Service
public class PatientTreatmentService {

	@Autowired
	PatientAppointmentRepository appointmentRepository;
	
	@Autowired
	PatientTreatmentRepository treatmentRepository;
	
	@Autowired
	PatientRegistrationRepository patientRegistrationRepository;
	private static final String Prescription = "prescriptionPhoto";


	public void addTreatment(Long appointment_id, PatientTreatment treatment, MultipartFile prescriptionPath) {
		Optional<PatientAppointment> treatmentOptinal = appointmentRepository.findById(appointment_id);
		if(treatmentOptinal.isEmpty()) {
	        throw new RuntimeException("Appointment not found with ID: " + appointment_id);
		}
		
		PatientAppointment appointment = treatmentOptinal.get();
		
		try {
			String projectRoot = System.getProperty("user.dir"); 
            String fileDirectory = projectRoot + File.separator + Prescription; 

            File directory = new File(fileDirectory); 
            if (!directory.exists()) { 
                directory.mkdirs();  }
            
            String filePath = fileDirectory + File.separator + prescriptionPath.getOriginalFilename();
            File destinationFile = new File(filePath);
            prescriptionPath.transferTo(destinationFile);

            treatment.setPrescription(Prescription + "/" + prescriptionPath.getOriginalFilename());
           
            treatment.setPatientAppointment(appointment);
            treatmentRepository.save(treatment); 
		}catch (IOException e) {
	        throw new RuntimeException("Error saving Treatment file: " + e.getMessage());
	    }
		
		
		
		/*if(treatmentOptinal.isPresent()) {
			PatientAppointment appointment = treatmentOptinal.get();
			treatment.setPatientAppointment(appointment);
		
		 return treatmentRepository.save(treatment);
    } else {
       
        throw new RuntimeException("Appointment not found for ID: " + appointment_id);
    }		

	}*/
}


	public Resource getTreatmentByAppointmentId(Long appointment_id) {
		Optional<PatientAppointment> appointmentOptional = appointmentRepository.findById(appointment_id);
	    
	    if (appointmentOptional.isEmpty()) {
	        throw new RuntimeException("No appointment found for ID: " + appointment_id);
	    }
	    
	    PatientAppointment appointment = appointmentOptional.get();

	    // Fetch treatments based on the PatientAppointment
	    List<PatientTreatment> treatmentList = treatmentRepository.findByPatientAppointment(appointment);

	    if (treatmentList.isEmpty()) {
	        throw new RuntimeException("No treatment records found for appointment ID: " + appointment_id);
	    }
	    
	    // Get the latest treatment (you can adjust this logic based on your requirements)
	    PatientTreatment latestTreatment = treatmentList.get(treatmentList.size() - 1);
	    
	    String relativeFilePath = latestTreatment.getPrescription();
	    if (relativeFilePath == null) {
	        throw new RuntimeException("No file associated with the latest treatment record.");
	    }
	    
	    String absoluteFilePath = System.getProperty("user.dir") + File.separator + relativeFilePath;
	    
	    try {
	        Path path = Paths.get(absoluteFilePath);
	        Resource resource = new UrlResource(path.toUri());

	        if (resource.isReadable()) {
	            return resource;
	        } else {
	            throw new RuntimeException("File not readable: " + absoluteFilePath);
	        }
	    } catch (Exception e) {
	        throw new RuntimeException("Error retrieving prescription", e);
	    }
	}


	public Resource getTreatmentPerscriptionByPatientId(Long patient_id) {
		
		Optional<PatientRegistration> patientOpt = patientRegistrationRepository.findById(patient_id);
	    
	    if (!patientOpt.isPresent()) {
	        throw new RuntimeException("Patient not found with ID: " + patient_id);
	    }
	    
	    PatientRegistration patient = patientOpt.get();
	    
	    // Retrieve all treatments related to the patient's appointments
	    List<PatientTreatment> treatmentList = treatmentRepository.findByPatientAppointmentPatient(patient);
	    
	    if (treatmentList.isEmpty()) {
	        throw new RuntimeException("No treatments found for patient with ID: " + patient_id);
	    }
	    
	    // Optionally, you can get the latest treatment record or a specific treatment based on your requirement
	    PatientTreatment latestTreatment = treatmentList.get(treatmentList.size() - 1);
	    
	    String relativeFilePath = latestTreatment.getPrescription();
	    if (relativeFilePath == null) {
	        throw new RuntimeException("No prescription file found for the latest treatment.");
	    }
	    
	    String absoluteFilePath = System.getProperty("user.dir") + File.separator + relativeFilePath;
	    
	    try {
	        Path path = Paths.get(absoluteFilePath);
	        Resource resource = new UrlResource(path.toUri());

	        if (resource.isReadable()) {
	            return resource;
	        } else {
	            throw new RuntimeException("Prescription file is not readable: " + absoluteFilePath);
	        }
	    } catch (Exception e) {
	        throw new RuntimeException("Error retrieving prescription for patient ID: " + patient_id, e);
	    }
		}
		
	   /* Optional<PatientAppointment> appointmentOptional = appointmentRepository.findById(appointment_id);

	    if (appointmentOptional.isEmpty()) {
	        throw new RuntimeException("No attendance records found for student ID: " + appointment_id);
	    }
	    
	    PatientTreatment latestTreatment = appointmentOptional.get(appointmentOptional.size()-1);
	    
	    String relativeFilePath = latestTreatment.getPrescription();
	    if (relativeFilePath == null) {
	        throw new RuntimeException("No file associated with the latest treatment record.");
	    }
	    String absoluteFilePath = System.getProperty("user.dir") + File.separator + relativeFilePath;
	    
	    try {
	        Path path = Paths.get(absoluteFilePath);
	        Resource resource = new UrlResource(path.toUri());

	        if (resource.isReadable()) {
	            return resource;
	        } else {
	            throw new RuntimeException("File not readable: " + absoluteFilePath);
	        }
	    } catch (Exception e) {
	        throw new RuntimeException("Error retrieving perscription", e);
	    }

	}*/
}
