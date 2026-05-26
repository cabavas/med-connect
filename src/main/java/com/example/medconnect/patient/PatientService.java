package com.example.medconnect.patient;

import com.example.medconnect.Role;
import com.example.medconnect.exception.NotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;

    public PatientService(PatientRepository patientRepository, PasswordEncoder passwordEncoder) {
        this.patientRepository = patientRepository;
        this.passwordEncoder = passwordEncoder;
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
                patient.getCpf(),
                patient.getRole()
        );
    }

    private Patient toEntity(PatientRequestDTO request) {
        Patient patient = new Patient();
        patient.setName(request.name());
        patient.setEmail(request.email());
        patient.setPhone(request.phone());
        patient.setCpf(request.cpf());
        patient.setRole(Role.DOCTOR);
        patient.setPassword(passwordEncoder.encode(request.password()));
        return patient;
    }
}
