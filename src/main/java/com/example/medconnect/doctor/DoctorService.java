package com.example.medconnect.doctor;

import com.example.medconnect.Role;
import com.example.medconnect.exception.NotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;

    public DoctorService(DoctorRepository doctorRepository, PasswordEncoder passwordEncoder) {
        this.doctorRepository = doctorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<DoctorResponseDTO> findAll() {
        return doctorRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public DoctorResponseDTO findById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Doctor not found with ID: " + id));

        return toResponse(doctor);
    }

    @Transactional
    public DoctorResponseDTO create(DoctorRequestDTO request) {
        Doctor doctor = toEntity(request);
        return toResponse(doctorRepository.save(doctor));
    }

    @Transactional
    public DoctorResponseDTO update(Long id, DoctorRequestDTO request) {
        Doctor existing = doctorRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Doctor not found with ID: " + id));
        existing.setName(request.name());
        existing.setCrm(request.crm());
        existing.setEmail(request.email());
        existing.setSpecialty(Specialty.valueOf(String.valueOf(request.specialty())));
        Doctor updated = doctorRepository.save(existing);
        return toResponse(updated);
    }

    @Transactional
    public void delete(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Doctor not found with ID: " + id));
        doctorRepository.delete(doctor);
    }

    private DoctorResponseDTO toResponse(Doctor doctor) {
        return new DoctorResponseDTO(
                doctor.getId(),
                doctor.getName(),
                doctor.getEmail(),
                doctor.getCrm(),
                doctor.getSpecialty(),
                doctor.getRole()
        );
    }

    private Doctor toEntity(DoctorRequestDTO request) {
        Doctor doctor = new Doctor();
        doctor.setName(request.name());
        doctor.setEmail(request.email());
        doctor.setCrm(request.crm());
        doctor.setSpecialty(Specialty.valueOf(String.valueOf(request.specialty())));
        doctor.setRole(Role.ROLE_DOCTOR);
        doctor.setPassword(passwordEncoder.encode(request.password()));

        return doctor;
    }
}
