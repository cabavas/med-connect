package com.example.medconnect.appointment;

import com.example.medconnect.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponseDTO> findAll() {
        return appointmentRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public AppointmentResponseDTO findById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Appointment not found with ID: " + id));
        return toResponse(appointment);
    }

    @Transactional
    public AppointmentResponseDTO create(AppointmentRequestDTO request) {
        Appointment appointment = toEntity(request);
        return toResponse(appointmentRepository.save(appointment));
    }

    @Transactional
    public AppointmentResponseDTO update(Long id, AppointmentRequestDTO request) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Appointment not found with ID: " + id));

        appointment.setPatientId(request.patientId());
        appointment.setDoctor(request.doctorId());
        appointment.setStatus(request.status());

        Appointment updated = appointmentRepository.save(appointment);
        return toResponse(updated);
    }

    @Transactional
    public void delete(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Appointment not found with ID: " + id));
        appointmentRepository.delete(appointment);
    }

    private AppointmentResponseDTO toResponse(Appointment appointment) {
        return new AppointmentResponseDTO(
                appointment.getId(),
                appointment.getPatientId(),
                appointment.getDoctor(),
                appointment.getStatus()
        );
    }

    private Appointment toEntity(AppointmentRequestDTO requestDTO) {
        Appointment appointment = new Appointment();
        appointment.setPatientId(requestDTO.patientId());
        appointment.setDoctor(requestDTO.doctorId());
        appointment.setStatus(requestDTO.status());
        return appointment;
    }
}
