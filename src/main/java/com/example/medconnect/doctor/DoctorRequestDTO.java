package com.example.medconnect.doctor;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

public record DoctorRequestDTO(
        @NotBlank
        String name,

        @NotBlank
        String email,

        @NotBlank
        String crm,

        @NotBlank
        String specialty,

        @NotBlank
        String password
) {
}
