package com.example.medconnect.patient;

import com.example.medconnect.Role;

public record PatientResponseDTO(
        String id,
        String name,
        String email,
        String phone,
        String cpf
) {
}
