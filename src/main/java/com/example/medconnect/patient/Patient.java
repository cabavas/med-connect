package com.example.medconnect.patient;

import com.example.medconnect.appointment.Appointment;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;
    private String phone;
    @Column(unique = true)
    private String cpf;
    private String role = "ROLE_PATIENT";

    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments = new ArrayList<>();

    public Patient() {
    }

    public Patient(String name, String email, String phone, String cpf, String role) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.cpf = cpf;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRole() {
        return role;
    }

    public List<Appointment> getAppointments() {
        return Collections.unmodifiableList(appointments);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(id, patient.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
