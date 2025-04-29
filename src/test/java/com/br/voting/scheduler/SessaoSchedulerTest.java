package com.br.voting.scheduler;

import com.br.voting.broker.SessaoFinalizadaProducer;
import com.br.voting.config.TestMockConfig;
import com.br.voting.helper.SessaoHelperTest;
import com.br.voting.helper.VotoHelperTest;
import com.br.voting.model.Sessao;
import com.br.voting.model.Voto;
import com.br.voting.model.event.SessaoFinalizadaEvent;
import com.br.voting.repository.SessaoRepository;
import com.br.voting.repository.VotoRepository;
import com.br.voting.service.SessaoService;
import com.br.voting.service.VotoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest(properties = {"spring.profiles.active=test"})
@Import(TestMockConfig.class)
class SessaoSchedulerTest {

    @Autowired
    private SessaoScheduler sessaoScheduler;

    @Autowired
    private SessaoService sessaoService;

    @Autowired
    private VotoService votoService;

    @Autowired
    private SessaoFinalizadaProducer sessaoFinalizadaProducer;

    @Autowired
    private VotoRepository votoRepository;
    @Autowired
    private SessaoRepository sessaoRepository;

    @Test
    void deveAbrirSessoesAgendadas() {
        // Arrange
        doNothing().when(sessaoRepository).abrirSessoesAgendadas();

        // Act
        sessaoScheduler.abrirSessoesAgendadas();

        // Assert
        verify(sessaoRepository, atLeastOnce()).abrirSessoesAgendadas();
    }

    @Test
    void deveFecharSessoesExpiradas() {
        // Arrange
        Sessao sessao = SessaoHelperTest.criarSessao();
        Voto voto = VotoHelperTest.criarVoto();

        when(sessaoService.listarSessoesExpiradas()).thenReturn(List.of(sessao));
        when(votoRepository.buscarPorIdSessao(sessao.getId())).thenReturn(List.of(voto));
        doNothing().when(sessaoFinalizadaProducer).enviarEvento(any(SessaoFinalizadaEvent.class));

        // Act
        sessaoScheduler.finalizarSessoesExpiradas();

        // Assert]
        verify(sessaoFinalizadaProducer, times(1)).enviarEvento(any(SessaoFinalizadaEvent.class));
    }
}
