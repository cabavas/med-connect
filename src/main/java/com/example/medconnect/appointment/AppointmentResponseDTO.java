package com.example.medconnect.appointment;

import com.example.medconnect.doctor.Doctor;
import com.example.medconnect.patient.Patient;

public record AppointmentResponseDTO(
        Long id,
        Patient patientId,
        Doctor doctorId,
        AppointmentStatus status
) {
}
