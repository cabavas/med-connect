package com.example.medconnect.appointment;

import jakarta.validation.constraints.NotNull;

public record AppointmentRequestDTO(
        @NotNull(message = "Doctor ID is required") Long doctorId,
        @NotNull(message = "Patient ID is required") Long patientId,
        AppointmentStatus status
) {}
