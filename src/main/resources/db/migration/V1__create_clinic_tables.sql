-- Tabela de pacientes
CREATE TABLE patients
(
    id    INT AUTO_INCREMENT PRIMARY KEY,
    name  VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20),
    cpf   VARCHAR(14)  NOT NULL UNIQUE,
    role  VARCHAR(20)  NOT NULL DEFAULT 'PATIENT'
);

-- Tabela de médicos
CREATE TABLE doctors
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(100) NOT NULL,
    email     VARCHAR(100) NOT NULL UNIQUE,
    crm       VARCHAR(20)  NOT NULL UNIQUE,
    specialty ENUM('CARDIOLOGY', 'DERMATOLOGY', 'NEUROLOGY', 'PEDIATRICS') NOT NULL,
    role      VARCHAR(20)  NOT NULL DEFAULT 'DOCTOR'
);

-- Tabela de consultas
CREATE TABLE appointments
(
    id               INT AUTO_INCREMENT PRIMARY KEY,
    patient_id       INT       NOT NULL,
    doctor_id        INT       NOT NULL,
    appointment_date TIMESTAMP NOT NULL,
    status           ENUM('PENDING', 'CONFIRMED', 'CONCLUDED', 'CANCELED') NOT NULL DEFAULT 'PENDING',
    created_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients (id) ON DELETE RESTRICT,
    FOREIGN KEY (doctor_id) REFERENCES doctors (id) ON DELETE RESTRICT
);

-- Índices opcionais para melhorar performance das consultas
CREATE INDEX idx_appointments_patient ON appointments (patient_id);
CREATE INDEX idx_appointments_doctor ON appointments (doctor_id);
CREATE INDEX idx_appointments_date ON appointments (appointment_date);