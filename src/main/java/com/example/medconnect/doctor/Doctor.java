package com.example.medconnect.doctor;

import com.example.medconnect.Role;
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
    private Role role = Role.DOCTOR;
    private String password;
    @OneToMany(mappedBy = "doctors")
    private List<Appointment> appointments = new ArrayList<>();

    public Doctor() {
    }

    public Doctor(Long id, String name, String email, String crm, Specialty specialty, Role role, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.crm = crm;
        this.specialty = specialty;
        this.role = role;
        this.password = password;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return Objects.equals(id, doctor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
