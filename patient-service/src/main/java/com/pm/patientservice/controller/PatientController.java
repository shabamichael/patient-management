package com.pm.patientservice.controller;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

    private final PatientService patientService;
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }
    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {
        // Call the service method to get all patients
        var patients = patientService.getAllPatients();
        // Return the list of patients as a response entity
        return ResponseEntity.ok().body( patients);
    }

    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(
            @Valid @RequestBody PatientRequestDTO patientRequestDTO) {

        // Call the service method to create a new patient
        var newPatient = patientService.createPatient(patientRequestDTO);
        // Return the created patient as a response entity
        return ResponseEntity.ok().body(newPatient);
    }

}
