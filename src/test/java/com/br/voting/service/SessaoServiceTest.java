package com.br.voting.service;

import com.br.voting.config.TestMockConfig;
import com.br.voting.dto.SessaoRequest;
import com.br.voting.dto.SessaoResponse;
import com.br.voting.enums.StatusSessao;
import com.br.voting.helper.SessaoHelperTest;
import com.br.voting.mapper.SessaoMapper;
import com.br.voting.model.Sessao;
import com.br.voting.repository.PautaRepository;
import com.br.voting.repository.SessaoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(properties = {"spring.profiles.active=test"})
@Import(TestMockConfig.class)
class SessaoServiceTest {

    @Autowired
    private SessaoService sessaoService;

    @Autowired
    private SessaoRepository sessaoRepository;

    @Autowired
    private SessaoMapper sessaoMapper;

    @Autowired
    private PautaRepository pautaRepository;

    @Test
    void deveCriarSessaoAbertaQuandoInicioNoPassado() {
        // Arrange
        Sessao sessao = SessaoHelperTest.criarSessao();
        SessaoResponse response = SessaoHelperTest.criarSessaoResponseFromModel(sessao);

        SessaoRequest request = SessaoHelperTest.criarSessaoRequest();
        request.setDataHoraInicio(request.getDataHoraInicio().minusMinutes(2)); // Faz o início estar no passado

        when(pautaRepository.buscarPorId(request.getIdPauta())).thenReturn(Optional.of(mock(com.br.voting.model.Pauta.class)));
        when(sessaoMapper.toModel(request)).thenReturn(sessao);
        when(sessaoMapper.toResponse(sessao)).thenReturn(response);

        // Act
        SessaoResponse result = sessaoService.criar(request);

        // Assert
        assertNotNull(result);
        assertEquals(StatusSessao.ABERTA, sessao.getStatus());
        verify(sessaoRepository, times(1)).salvar(sessao);
    }

    @Test
    void deveCriarSessaoAguardandoQuandoInicioNoFuturo() {
        // Arrange
        Sessao sessao = SessaoHelperTest.criarSessao();
        SessaoResponse response = SessaoHelperTest.criarSessaoResponseFromModel(sessao);

        SessaoRequest request = SessaoHelperTest.criarSessaoRequest();
        request.setDataHoraInicio(request.getDataHoraInicio().plusMinutes(5)); // Início futuro

        when(pautaRepository.buscarPorId(request.getIdPauta())).thenReturn(Optional.of(mock(com.br.voting.model.Pauta.class)));
        when(sessaoMapper.toModel(request)).thenReturn(sessao);
        when(sessaoMapper.toResponse(sessao)).thenReturn(response);

        // Act
        SessaoResponse result = sessaoService.criar(request);

        // Assert
        assertNotNull(result);
        assertEquals(StatusSessao.ABERTA, sessao.getStatus());
        verify(sessaoRepository, times(1)).salvar(sessao);
    }

    @Test
    void deveListarTodasAsSessoes() {
        // Arrange
        Sessao sessao = SessaoHelperTest.criarSessao();

        when(sessaoRepository.listarTodas()).thenReturn(List.of(sessao));
        when(sessaoMapper.toResponse(any(Sessao.class))).thenReturn(new SessaoResponse());

        // Act
        List<SessaoResponse> sessoes = sessaoService.listarTodas();

        // Assert
        assertFalse(sessoes.isEmpty());
        verify(sessaoRepository, times(1)).listarTodas();
    }

    @Test
    void deveBuscarSessaoPorIdComSucesso() {
        // Arrange
        UUID id = UUID.randomUUID();
        Sessao sessao = SessaoHelperTest.criarSessao();

        when(sessaoRepository.buscarPorId(id)).thenReturn(Optional.of(sessao));
        when(sessaoMapper.toResponse(any(Sessao.class))).thenReturn(new SessaoResponse());

        // Act
        SessaoResponse response = sessaoService.buscarPorId(id);

        // Assert
        assertNotNull(response);
        verify(sessaoRepository, times(1)).buscarPorId(id);
    }

    @Test
    void deveAbrirSessoesAgendadas() {
        // Arrange
        doNothing().when(sessaoRepository).abrirSessoesAgendadas();

        // Act
        sessaoService.abrirSessoesAgendadas();

        // Assert
        verify(sessaoRepository, atLeastOnce()).abrirSessoesAgendadas();
    }

    @Test
    void deveListarSessoesExpiradas() {
        // Arrange
        Sessao sessao = SessaoHelperTest.criarSessao();

        when(sessaoRepository.listarSessoesExpiradas()).thenReturn(List.of(sessao));

        // Act
        List<Sessao> expiradas = sessaoService.listarSessoesExpiradas();

        // Assert
        assertFalse(expiradas.isEmpty());
        verify(sessaoRepository, atLeastOnce()).listarSessoesExpiradas();
    }

    @Test
    void deveFinalizarSessaoComSucesso() {
        // Arrange
        UUID id = UUID.randomUUID();
        doNothing().when(sessaoRepository).finalizarSessao(id);

        // Act
        sessaoService.finalizarSessao(id);

        // Assert
        verify(sessaoRepository, atLeastOnce()).finalizarSessao(id);
    }
}
