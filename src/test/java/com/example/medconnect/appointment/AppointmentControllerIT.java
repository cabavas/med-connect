package com.example.medconnect.appointment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AppointmentControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AppointmentService appointmentService;

    @Test
    @DisplayName("Should allow doctors to conclude an appointment and return 200 OK")
    @WithMockUser(username = "doctor_user", roles = {"DOCTOR"}) // Simula um usuário logado com a Role DOCTOR
    void concludeWithDoctorRole() throws Exception {
        Long appointmentId = 1L;

        // Como o método da service é void, dizemos para o Mockito não fazer nada quando for chamado
        doNothing().when(appointmentService).conclude(appointmentId);

        // Dispara o PUT simulando o navegador ou o Angular
        mockMvc.perform(put("/api/appointments/{id}/conclude", appointmentId))
                .andExpect(status().isOk()); // Valida se o status retornado é 200 OK
    }

    @Test
    @DisplayName("Should block patients trying to conclude an appointment and return 403 Forbidden")
    @WithMockUser(username = "patient_user", roles = {"PATIENT"}) // Simula um usuário logado com a Role PATIENT
    void concludeWithPatientRole() throws Exception {
        Long appointmentId = 1L;

        // Dispara o PUT e valida se o Spring Security barrou com 403 ANTES mesmo de chamar a Service
        mockMvc.perform(put("/api/appointments/{id}/conclude", appointmentId))
                .andExpect(status().isForbidden()); // Valida se foi bloqueado com 403 Forbidden
    }
}
