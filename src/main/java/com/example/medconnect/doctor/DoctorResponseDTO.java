package com.example.medconnect.doctor;

import com.example.medconnect.Role;

public record DoctorResponseDTO(
        Long id,
        String name,
        String email,
        String crm,
        Specialty specialty,
        Role role
) {
}
