package com.example.medconnect.appointment;

import com.example.medconnect.doctor.Doctor;
import com.example.medconnect.doctor.DoctorRepository;
import com.example.medconnect.exception.NotFoundException;
import com.example.medconnect.patient.Patient;
import com.example.medconnect.patient.PatientRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private AppointmentService appointmentService;

    @Test
    @DisplayName("Should schedule an appointment successfully when patient and doctor exists")
    void createSuccess() {
        AppointmentRequestDTO request = new AppointmentRequestDTO(1L, 1L, AppointmentStatus.PENDING);

        Patient mockPatient = new Patient();
        mockPatient.setId(1L);
        mockPatient.setName("John Doe");

        Doctor mockDoctor = new Doctor();
        mockDoctor.setId(1L);
        mockDoctor.setName("Dr. House");

        Appointment mockAppointment = new Appointment();
        mockAppointment.setId(10L);
        mockAppointment.setPatientId(mockPatient);
        mockAppointment.setDoctor(mockDoctor);
        mockAppointment.setStatus(AppointmentStatus.PENDING);

        when(patientRepository.findById(1L)).thenReturn(Optional.of(mockPatient));
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(mockDoctor));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(mockAppointment);

        AppointmentResponseDTO response = appointmentService.create(request);

        assertNotNull(response);
        assertEquals(10L, response.id());
        assertEquals("John Doe", response.patientName());
        assertEquals("Dr. House", response.doctorName());
        assertEquals(AppointmentStatus.PENDING, response.status());

        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }

    @Test
    @DisplayName("Should throw NotFoundException when patient does not exists")
    void createPatientNotFound() {
        AppointmentRequestDTO request = new AppointmentRequestDTO(1L, 99L, AppointmentStatus.PENDING);

        when(patientRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            appointmentService.create(request);
        });

        verify(appointmentRepository, never()).save(any(Appointment.class));
    }
}
