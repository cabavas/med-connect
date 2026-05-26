package com.example.medconnect.appointment;

import com.example.medconnect.doctor.Doctor;
import com.example.medconnect.patient.Patient;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    public Appointment() {
    }

    public Appointment(Long id, Patient patientId, Doctor doctor, AppointmentStatus status) {
        this.id = id;
        this.patient = patientId;
        this.doctor = doctor;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Patient getPatientId() {
        return patient;
    }

    public void setPatientId(Patient patientId) {
        this.patient = patientId;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
