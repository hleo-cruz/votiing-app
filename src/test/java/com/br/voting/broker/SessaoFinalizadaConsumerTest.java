package com.br.voting.broker;

import com.br.voting.config.TestMockConfig;
import com.br.voting.enums.StatusPauta;
import com.br.voting.model.event.ResultadoFinal;
import com.br.voting.model.event.ResultadoVotacao;
import com.br.voting.model.event.SessaoFinalizadaEvent;
import com.br.voting.repository.PautaRepository;
import com.br.voting.service.PautaService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.*;

@SpringBootTest(properties = {"spring.profiles.active=test"})
@Import(TestMockConfig.class)
class SessaoFinalizadaConsumerTest {

    @Autowired
    private SessaoFinalizadaConsumer consumer;

    @Autowired
    private PautaService pautaService;
    @Autowired
    private PautaRepository pautaRepository;

    @Test
    void deveAtualizarStatusDaPautaParaAprovada() {
        // Arrange
        SessaoFinalizadaEvent evento = new SessaoFinalizadaEvent(
                UUID.randomUUID(),
                UUID.randomUUID(),
                LocalDateTime.now(),
                new ResultadoVotacao(10L, 2L, ResultadoFinal.SIM)
        );

        doNothing().when(pautaRepository).atualizarStatus(evento.getIdPauta(), StatusPauta.APROVADA.name());

        ConsumerRecord<String, SessaoFinalizadaEvent> record =
                new ConsumerRecord<>("sessao.finalizada", 0, 0L, null, evento);

        // Act
        consumer.consumirMensagem(record, () -> {});

        // Assert
        verify(pautaRepository, times(1)).atualizarStatus(evento.getIdPauta(), StatusPauta.APROVADA.name());
    }

    @Test
    void deveAtualizarStatusDaPautaParaReprovada() {
        // Arrange
        SessaoFinalizadaEvent evento = new SessaoFinalizadaEvent(
                UUID.randomUUID(),
                UUID.randomUUID(),
                LocalDateTime.now(),
                new ResultadoVotacao(3L, 9L, ResultadoFinal.NAO)
        );

        doNothing().when(pautaRepository).atualizarStatus(evento.getIdPauta(), StatusPauta.REPROVADA.name());

        ConsumerRecord<String, SessaoFinalizadaEvent> record =
                new ConsumerRecord<>("sessao.finalizada", 0, 0L, null, evento);

        // Act
        consumer.consumirMensagem(record, () -> {});

        // Assert
        verify(pautaRepository, times(1)).atualizarStatus(evento.getIdPauta(), StatusPauta.REPROVADA.name());
    }
}
