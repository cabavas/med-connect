package com.example.medconnect.patient;

import com.example.medconnect.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> findAll() {
        return patientRepository.findAll().stream().map(this::toResponse).toList();
    }

    public PatientResponseDTO findById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Patient not found with ID: " + id));
        return toResponse(patient);
    }

    public PatientResponseDTO create(PatientRequestDTO request) {
        Patient patient = toEntity(request);
        Patient saved = patientRepository.save(patient);
        return toResponse(saved);
    }

    public PatientResponseDTO update(Long id, PatientRequestDTO request) {
        Patient existing = patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Patient not found with ID: " + id));

        existing.setPhone(request.phone());
        existing.setName(request.name());
        existing.setCpf(request.cpf());
        existing.setEmail(request.email());
        Patient updated = patientRepository.save(existing);
        return toResponse(updated);
    }

    public void delete(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Patient not found with ID: " + id));
    }

    private PatientResponseDTO toResponse(Patient patient) {
        return new PatientResponseDTO(
                String.valueOf(patient.getId()), // id
                patient.getName(),
                patient.getEmail(),
                patient.getPhone(),
                patient.getCpf()
        );
    }

    private Patient toEntity(PatientRequestDTO request) {
        Patient patient = new Patient();
        patient.setCpf(request.cpf());
        patient.setEmail(request.email());
        patient.setName(request.name());
        patient.setPhone(request.phone());

        return patient;
    }
}
