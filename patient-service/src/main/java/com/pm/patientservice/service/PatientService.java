package com.pm.patientservice.service;


import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.exception.EmailAlreadyExistException;
import com.pm.patientservice.exception.PatientNotFoundException;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();

        //Convert the list of Patient entities to a list of PatientResponseDTOs
        // Map the list of Patient entities to a list of PatientResponseDTOs
        // Using Java 8 Stream API to interate over the list of patients
        // and convert each Patient entity to a PatientResponseDTO
        List<PatientResponseDTO> patientResponseDTOS =

                // Stream the list of patients
                patients.stream()
                        // Map each Patient entity to a PatientResponseDTO
                .map(patient -> PatientMapper.toPatientResponseDTO(patient))
                                .toList();
        return patientResponseDTOS;


    }
    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistException("A patient with email " + patientRequestDTO.getEmail() + " already exists");
        }
        Patient newPatient = patientRepository.save(
                PatientMapper.toPatientEntityModel(patientRequestDTO));
        return PatientMapper.toPatientResponseDTO(newPatient);

    }

    public PatientResponseDTO updatePatient(
            UUID patientId, PatientRequestDTO patientRequestDTO) {
        // Check if the patient exists

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Patient with id " + patientId + " not found"));
        //Check if the email already exists
        if(patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail() , patientId)) {
            throw new EmailAlreadyExistException("A patient with email "
                    + patientRequestDTO.getEmail() + " already exists");
        }
        // Update the patient details
        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        // Save the updated patient
        Patient updatedPatient = patientRepository.save(patient);
        return PatientMapper.toPatientResponseDTO(updatedPatient);
    }

    public void deletePatient(UUID patientId){
        // Check if the patient exists
        Patient patient = patientRepository.findById(patientId).orElseThrow(
                () -> new PatientNotFoundException("Patient with id " + patientId + " not found")
        );
        if (patientId == null) {
            throw new IllegalArgumentException("Patient id must not be null");
        }
        // Delete the patient
        patientRepository.deleteById(patientId);
    }
}