package com.example.medconnect.appointment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class AppointmentConsumer {
    private static final Logger log = LoggerFactory.getLogger(AppointmentConsumer.class);
    private final AppointmentRepository repository;
    private final RestClient restClient;

    public AppointmentConsumer(AppointmentRepository repository, RestClient restClient) {
        this.repository = repository;
        this.restClient = restClient;
    }

    @RabbitListener(queues = "appointment.concluded")
    public void processAppointmentOutputs(AppointmentMessageDTO message) {
        log.info("[WORKER ASYNC] - Iniciando processamento pós-consulta para a Consulta ID: {}", message.appointmentId());

        try {
            // 1. Simulação do Envio de E-mail para o Paciente
            log.info("[WORKER ASYNC] - Preparando e-mail com a receita digital para o paciente...");
            Thread.sleep(2000); // Trava por 2 segundos simulando o envio real
            log.info("[WORKER ASYNC] - E-mail enviado com sucesso para: {}", message.patientEmail());

            // 2. Simulação da Geração do Prontuário Eletrônico (PDF)
            log.info("[WORKER ASYNC] - Gerando histórico e arquivo PDF do atendimento médico...");
            Thread.sleep(3000); // Trava por 3 segundos simulando a escrita do arquivo
            log.info("[WORKER ASYNC] - PDF gerado e arquivado com sucesso no sistema clínico.");

            // 3. Simulação do Faturamento Interno (Financeiro)
            log.info("[WORKER ASYNC] - Enviando dados de coparticipação para o plano de saúde...");
            Thread.sleep(1500); // Trava por 1.5 segundos
            log.info("[WORKER ASYNC] - Faturamento processado para o Médico CRM: {}", message.doctorCrm());

            log.info("[WORKER ASYNC] - Fluxo assíncrono finalizado com SUCESSO para a Consulta ID: {}\n", message.appointmentId());

        } catch (InterruptedException e) {
            log.error("Erro crítico no processamento em segundo plano", e);
            Thread.currentThread().interrupt();
        }
    }
}
