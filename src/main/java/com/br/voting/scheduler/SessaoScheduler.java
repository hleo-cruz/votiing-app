package com.br.voting.scheduler;

import com.br.voting.broker.SessaoFinalizadaProducer;
import com.br.voting.model.Sessao;
import com.br.voting.model.event.ResultadoVotacao;
import com.br.voting.model.event.SessaoFinalizadaEvent;
import com.br.voting.repository.SessaoRepository;
import com.br.voting.service.SessaoService;
import com.br.voting.service.VotoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SessaoScheduler {

    private final VotoService votoService;
    private final SessaoService sessaoService;
    private final SessaoFinalizadaProducer kafkaProducer;

    @Scheduled(fixedRate = 60000)
    public void abrirSessoesAgendadas() {
        try {
            sessaoService.abrirSessoesAgendadas();
        } catch (Exception e) {
            log.error("ABRIR_SESSOES_AGENDADAS - ERRO - Falha ao abrir sessões - message={}", e.getMessage(), e);
        }
    }

    @Scheduled(fixedRate = 60000)
    public void finalizarSessoesExpiradas() {
        try {
            log.info("FINALIZAR_SESSOES_EXPIRADAS - INICIO");

            List<Sessao> expiradas = sessaoService.listarSessoesExpiradas();

            for (Sessao sessao : expiradas) {
                ResultadoVotacao resultado = votoService.apurarResultado(sessao.getId());
                SessaoFinalizadaEvent evento = new SessaoFinalizadaEvent(
                        sessao.getId(),
                        sessao.getIdPauta(),
                        sessao.getDataHoraFim(),
                        resultado
                );
                kafkaProducer.enviarEvento(evento);

                sessaoService.finalizarSessao(sessao.getId());
                log.info("SESSAO_FINALIZADA - Evento publicado e sessão encerrada - id={}", sessao.getId());
            }

            log.info("FINALIZAR_SESSOES_EXPIRADAS - SUCESSO");

        } catch (Exception e) {
            log.error("FINALIZAR_SESSOES_EXPIRADAS - ERRO - message={}", e.getMessage(), e);
        }
    }

}
