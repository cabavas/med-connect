package com.example.medconnect.appointment;

import com.example.medconnect.doctor.Doctor;
import com.example.medconnect.patient.Patient;

public record AppointmentResponseDTO(
        Long id,
        Long patientId,
        Long doctorId,
        String patientName,
        String doctorName,
        AppointmentStatus status
) {
}
