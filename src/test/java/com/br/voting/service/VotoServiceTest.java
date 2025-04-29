package com.br.voting.service;

import com.br.voting.config.TestMockConfig;
import com.br.voting.dto.VotoRequest;
import com.br.voting.dto.VotoResponse;
import com.br.voting.enums.EscolhaVoto;
import com.br.voting.helper.AssociadoHelperTest;
import com.br.voting.helper.SessaoHelperTest;
import com.br.voting.helper.VotoHelperTest;
import com.br.voting.mapper.VotoMapper;
import com.br.voting.model.Associado;
import com.br.voting.model.Sessao;
import com.br.voting.model.Voto;
import com.br.voting.model.event.ResultadoFinal;
import com.br.voting.model.event.ResultadoVotacao;
import com.br.voting.repository.AssociadoRepository;
import com.br.voting.repository.SessaoRepository;
import com.br.voting.repository.VotoRepository;
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
class VotoServiceTest {

    @Autowired
    private VotoService votoService;

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private VotoMapper votoMapper;
    @Autowired
    private AssociadoRepository associadoRepository;

    @Autowired
    private SessaoRepository sessaoRepository;

    @Test
    void deveCriarVotoComSucesso() {
        // Arrange
        Voto voto = VotoHelperTest.criarVoto();
        Sessao sessao = SessaoHelperTest.criarSessao();
        Associado associado = AssociadoHelperTest.criarAssociado();

        VotoRequest request = VotoHelperTest.criarVotoRequest();
        request.setIdAssociado(associado.getId());
        request.setIdSessao(sessao.getId());

        VotoResponse response = VotoHelperTest.criarVotoReponseFromModel(voto);

        when(associadoRepository.buscarPorId(associado.getId())).thenReturn(Optional.of(associado));
        when(votoMapper.toModel(request)).thenReturn(voto);
        when(sessaoRepository.buscarPorId(sessao.getId())).thenReturn(Optional.of(sessao));
        when(votoMapper.toResponse(voto)).thenReturn(response);

        // Act
        VotoResponse result = votoService.criar(request);

        // Assert
        assertNotNull(result);
        verify(votoRepository, times(1)).salvar(voto);
    }

    @Test
    void deveListarTodosOsVotos() {
        // Arrange
        Voto voto = VotoHelperTest.criarVoto();

        when(votoRepository.listarTodos()).thenReturn(List.of(voto));
        when(votoMapper.toResponse(any(Voto.class))).thenReturn(new VotoResponse());

        // Act
        List<VotoResponse> votos = votoService.listarTodos();

        // Assert
        assertFalse(votos.isEmpty());
        verify(votoRepository, times(1)).listarTodos();
    }

    @Test
    void deveBuscarVotoPorIdComSucesso() {
        // Arrange
        UUID id = UUID.randomUUID();
        Voto voto = VotoHelperTest.criarVoto();

        when(votoRepository.buscarPorId(id)).thenReturn(Optional.of(voto));
        when(votoMapper.toResponse(any(Voto.class))).thenReturn(new VotoResponse());

        // Act
        VotoResponse response = votoService.buscarPorId(id);

        // Assert
        assertNotNull(response);
        verify(votoRepository, times(1)).buscarPorId(id);
    }

    @Test
    void deveApurarResultadoCorretamente() {
        // Arrange
        UUID idSessao = UUID.randomUUID();
        Voto votoSim = VotoHelperTest.criarVoto();
        votoSim.setEscolha(EscolhaVoto.SIM);

        Voto votoNao = VotoHelperTest.criarVoto();
        votoNao.setEscolha(EscolhaVoto.NAO);

        when(votoRepository.buscarPorIdSessao(idSessao)).thenReturn(List.of(votoSim, votoNao, votoSim));

        // Act
        ResultadoVotacao resultado = votoService.apurarResultado(idSessao);

        // Assert
        assertEquals(2, resultado.getVotosSim());
        assertEquals(1, resultado.getVotosNao());
        assertEquals(ResultadoFinal.SIM, resultado.getResultado());
        verify(votoRepository, times(1)).buscarPorIdSessao(idSessao);
    }
}
