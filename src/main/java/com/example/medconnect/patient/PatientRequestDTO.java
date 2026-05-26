package com.example.medconnect.patient;

import com.example.medconnect.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PatientRequestDTO(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email,

        String phone,

        @NotBlank(message = "CPF is required")
        String cpf,

        @NotBlank(message = "Password is required")
        String password,

        Role role
) {
}
