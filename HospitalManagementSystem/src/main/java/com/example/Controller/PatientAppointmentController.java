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

import com.example.Entity.PatientAppointment;
import com.example.Service.PatientAppointmentService;

@RestController
public class PatientAppointmentController {
	
	@Autowired
	PatientAppointmentService appointmentService;
	
	
	@PostMapping("/scheduleAppointment/{patient_id}")
	public ResponseEntity<PatientAppointment> scheduleAppointment (@PathVariable Long patient_id, @RequestBody PatientAppointment appointment){
		PatientAppointment saveAppointment = appointmentService.scheduleAppointment(patient_id,appointment);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(saveAppointment);
	}
	
	@GetMapping("/getAppointmentById/{patient_id}")
	public  ResponseEntity<PatientAppointment> getAppoinmentById(@PathVariable Long patient_id) {
		PatientAppointment appointment = appointmentService.getAppointmentById(patient_id);
	    return ResponseEntity.ok(appointment);
		
		
	}
	@DeleteMapping("/cancleAppointment/{id}")
	 public String cancleAppointment(@PathVariable Long id) {
		return  appointmentService.cancleAppointment(id);
		 
		 
	 }
	@GetMapping("/getAllAppointment")
	public ResponseEntity<List<PatientAppointment>> getAllAppointments() {
        List<PatientAppointment> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }
	
	@PutMapping("/updateAppointment/{patient_id}")
	public ResponseEntity<String> updateAppointment(@PathVariable Long patient_id,@RequestBody PatientAppointment appointment){
		
		String response = appointmentService.updateStudent(patient_id,appointment);
		
		return ResponseEntity.ok(response);
		
	}
	
}

