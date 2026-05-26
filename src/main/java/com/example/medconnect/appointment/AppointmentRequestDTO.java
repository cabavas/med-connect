package com.example.medconnect.appointment;

import com.example.medconnect.doctor.Doctor;
import com.example.medconnect.patient.Patient;

public record AppointmentRequestDTO(
        Doctor doctorId,
        Patient patientId,
        AppointmentStatus status
) {
}
