package com.example.medconnect.patient;

import com.example.medconnect.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

public record PatientRequestDTO(
        @NotBlank
        String name,

        @NotBlank
        String email,

        String phone,

        @NotBlank
        String cpf,

        @NotBlank
        String password,

        Role role
) {
}
