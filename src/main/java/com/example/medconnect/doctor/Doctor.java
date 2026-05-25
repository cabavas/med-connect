package com.example.medconnect.doctor;

import com.example.medconnect.appointment.Appointment;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String crm;
    @Enumerated(EnumType.STRING)
    private Specialty specialty;
    private String role = "DOCTOR";
    @OneToMany
    private List<Appointment> appointments = new ArrayList<>();

    public Doctor() {
    }

    public Doctor(Long id, String name, String email, String crm, Specialty specialty, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.crm = crm;
        this.specialty = specialty;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return Objects.equals(id, doctor.id) && Objects.equals(name, doctor.name) && Objects.equals(email, doctor.email) && Objects.equals(crm, doctor.crm) && specialty == doctor.specialty && Objects.equals(role, doctor.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, crm, specialty, role);
    }
}
