package com.example.medconnect.appointment;

public record AppointmentMessageDTO(
        Long appointmentId,
        String patientEmail,
        String doctorCrm
) {
}
