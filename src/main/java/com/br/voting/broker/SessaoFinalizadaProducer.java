package com.br.voting.broker;

import com.br.voting.model.event.SessaoFinalizadaEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SessaoFinalizadaProducer {

    @Value("${app.kafka.topicos.sessao-finalizada}")
    private String topico;

    private final KafkaTemplate<String, SessaoFinalizadaEvent> kafkaTemplate;

    public void enviarEvento(SessaoFinalizadaEvent evento) {
        try {
            kafkaTemplate.send(topico, evento);
            log.info("ENVIAR_EVENTO_SESSAO_FINALIZADA - SUCESSO - idSessao={}", evento.getIdSessao());
        } catch (Exception e) {
            log.error("ENVIAR_EVENTO_SESSAO_FINALIZADA - ERRO - Falha ao enviar evento - idSessao={}, message={}",
                    evento.getIdSessao(), e.getMessage(), e);
        }
    }
}
