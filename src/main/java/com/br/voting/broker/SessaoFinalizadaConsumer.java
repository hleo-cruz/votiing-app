package com.br.voting.broker;

import com.br.voting.enums.StatusPauta;
import com.br.voting.model.event.ResultadoFinal;
import com.br.voting.model.event.SessaoFinalizadaEvent;
import com.br.voting.service.PautaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SessaoFinalizadaConsumer {

    private final PautaService pautaService;

    @KafkaListener(
            topics = "${app.kafka.topicos.sessao-finalizada}",
            groupId = "${app.kafka.grupos.sessao-finalizada}",
            containerFactory = "${app.kafka.containers.sessao-finalizada}")
    public void consumirMensagem(ConsumerRecord<String, SessaoFinalizadaEvent> record, Acknowledgment ack) {
        try {
            SessaoFinalizadaEvent evento = record.value();
            ResultadoFinal resultado = evento.getResultado().getResultado();

            StatusPauta status = switch (resultado) {
                case SIM -> StatusPauta.APROVADA;
                case NAO -> StatusPauta.REPROVADA;
                default -> StatusPauta.PENDENTE;
            };

            pautaService.atualizarStatus(evento.getIdPauta(), status);

            ack.acknowledge();
        } catch (Exception e) {
            log.error("Erro ao processar evento - message={}", e.getMessage(), e);
        }
    }
}

