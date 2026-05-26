package com.example.medconnect.doctor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DoctorRequestDTO(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid") // Boa prática adicionar validação de formato
        String email,

        @NotBlank(message = "CRM is required")
        String crm,

        @NotNull(message = "Specialty is required") // Mudado de @NotBlank para @NotNull
        Specialty specialty,                        // Alterado o tipo de String para Specialty

        @NotBlank(message = "Password is required")
        String password
) {
}