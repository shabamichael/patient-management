package com.pm.patientservice.service;


import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Patient newPatient = patientRepository.save(
                PatientMapper.toPatientEntityModel(patientRequestDTO));
        return PatientMapper.toPatientResponseDTO(newPatient);

    }
}
