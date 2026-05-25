package com.example.medconnect.doctor;

public record DoctorResponseDTO(
        String name,
        String email,
        String crm,
        Specialty specialty
) {
}
