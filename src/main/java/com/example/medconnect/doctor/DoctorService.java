package com.example.medconnect.doctor;

import com.example.medconnect.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
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
        existing.setSpecialty(Specialty.valueOf(request.specialty()));
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
                doctor.getName(),
                doctor.getCrm(),
                doctor.getEmail(),
                doctor.getSpecialty()
        );
    }

    private Doctor toEntity(DoctorRequestDTO request) {
        Doctor doctor = new Doctor();
        doctor.setName(request.name());
        doctor.setEmail(request.email());
        doctor.setCrm(request.crm());

        return doctor;
    }
}
