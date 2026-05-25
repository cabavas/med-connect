package com.example.medconnect.doctor;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

public record DoctorRequestDTO(
        @NotBlank
        String name,

        @Column(unique = true)
        String email,

        @Column(unique = true)
        String crm,

        @NotBlank
        String specialty
) {
}
