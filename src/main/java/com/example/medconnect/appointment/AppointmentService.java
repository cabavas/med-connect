package com.example.medconnect.appointment;

import com.example.medconnect.doctor.Doctor;
import com.example.medconnect.doctor.DoctorRepository;
import com.example.medconnect.patient.Patient;
import com.example.medconnect.patient.PatientRepository;
import com.example.medconnect.exception.NotFoundException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final RabbitTemplate rabbitTemplate;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              PatientRepository patientRepository,
                              DoctorRepository doctorRepository,
                              RabbitTemplate rabbitTemplate) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.rabbitTemplate = rabbitTemplate;
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
        Patient patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new NotFoundException("Patient not found with ID: " + request.patientId()));
        Doctor doctor = doctorRepository.findById(request.doctorId())
                .orElseThrow(() -> new NotFoundException("Doctor not found with ID: " + request.doctorId()));

        Appointment appointment = new Appointment();
        appointment.setPatientId(patient);
        appointment.setDoctor(doctor);
        appointment.setStatus(request.status() != null ? request.status() : AppointmentStatus.PENDING);

        return toResponse(appointmentRepository.save(appointment));
    }

    @Transactional
    public AppointmentResponseDTO update(Long id, AppointmentRequestDTO request) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Appointment not found with ID: " + id));

        Patient patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new NotFoundException("Patient not found with ID: " + request.patientId()));
        Doctor doctor = doctorRepository.findById(request.doctorId())
                .orElseThrow(() -> new NotFoundException("Doctor not found with ID: " + request.doctorId()));

        appointment.setPatientId(patient);
        appointment.setDoctor(doctor);
        appointment.setStatus(request.status());

        return toResponse(appointmentRepository.save(appointment));
    }

    @Transactional
    public void delete(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Appointment not found with ID: " + id));
        appointmentRepository.delete(appointment);
    }

    @Transactional
    public void conclude(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Appointment not found with ID: " + id));

        appointment.setStatus(AppointmentStatus.CONCLUDED);
        appointmentRepository.save(appointment);

        AppointmentMessageDTO message = new AppointmentMessageDTO(
                appointment.getId(),
                appointment.getPatientId().getEmail(),
                appointment.getDoctor().getCrm()
        );

        // Garante segurança transacional: Só envia pro RabbitMQ se o MySQL commitar com sucesso!
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                rabbitTemplate.convertAndSend("appointment.concluded", message);
            }
        });
    }

    private AppointmentResponseDTO toResponse(Appointment appointment) {
        return new AppointmentResponseDTO(
                appointment.getId(),
                appointment.getPatientId().getId(),
                appointment.getDoctor().getId(),
                appointment.getPatientId().getName(),
                appointment.getDoctor().getName(),
                appointment.getStatus()
        );
    }
}