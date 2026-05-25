package com.example.medconnect.appointment;

import com.example.medconnect.doctor.Doctor;
import com.example.medconnect.patient.Patient;
import jakarta.persistence.*;

@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patientId;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctorId;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;
}
