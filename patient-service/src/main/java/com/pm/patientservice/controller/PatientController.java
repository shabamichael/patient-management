package com.pm.patientservice.controller;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/patients")
@Tag(name="Patient", description = "Patients Management API")
public class PatientController {

    private final PatientService patientService;
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }
    @GetMapping
    @Operation(summary = "Get all patients")
    public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {
        // Call the service method to get all patients
        var patients = patientService.getAllPatients();
        // Return the list of patients as a response entity
        return ResponseEntity.ok().body( patients);
    }

    @PostMapping
    @Operation(summary = "Create a new patient")
    public ResponseEntity<PatientResponseDTO> createPatient(
            @Valid @RequestBody PatientRequestDTO patientRequestDTO) {

        // Call the service method to create a new patient
        var newPatient = patientService.createPatient(patientRequestDTO);
        // Return the created patient as a response entity
        return ResponseEntity.ok().body(newPatient);
    }

    @PutMapping("/{patientId}")
    @Operation(summary = "Update an existing patient")
    public ResponseEntity<PatientResponseDTO> updatePatient(
            @PathVariable("patientId") UUID patientId,
            @RequestBody PatientRequestDTO patientRequestDTO) {
        // Call the service method to update the patient
        PatientResponseDTO updatedPatient =
                patientService.updatePatient(patientId, patientRequestDTO);
        // Return the updated patient as a response entity
        return ResponseEntity.ok().body(updatedPatient);
    }

    @DeleteMapping("/{patientId}")
    @Operation(summary = "Delete a patient")
    public ResponseEntity<Void>  deletePatient(@PathVariable("patientId") UUID patientId) {
        // Call the service method to delete the patient
        patientService.deletePatient(patientId);
        // Return a response entity with no content
        return ResponseEntity.noContent().build();

    }
}
