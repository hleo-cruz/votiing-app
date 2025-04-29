package com.br.voting.service;

import com.br.voting.config.TestMockConfig;
import com.br.voting.dto.PautaRequest;
import com.br.voting.dto.PautaResponse;
import com.br.voting.enums.StatusPauta;
import com.br.voting.exception.EntidadeNaoEncontradaException;
import com.br.voting.helper.PautaHelperTest;
import com.br.voting.mapper.PautaMapper;
import com.br.voting.model.Pauta;
import com.br.voting.repository.PautaRepository;
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
class PautaServiceTest {

    @Autowired
    private PautaService pautaService;

    @Autowired
    private PautaRepository pautaRepository;

    @Autowired
    private PautaMapper pautaMapper;

    @Test
    void deveCriarPautaComSucesso() {
        // Arrange
        PautaRequest request = PautaHelperTest.criarPautaRequest();
        Pauta pauta = PautaHelperTest.criarPauta();
        PautaResponse response = PautaHelperTest.criarPautaResponseFromModel(pauta);

        when(pautaMapper.toModel(request)).thenReturn(pauta);
        when(pautaMapper.toResponse(pauta)).thenReturn(response);


        // Act
        PautaResponse result = pautaService.criar(request);

        // Assert
        assertNotNull(result);
        verify(pautaRepository, times(1)).salvar(pauta);
    }

    @Test
    void deveRetornarListaDePautas() {
        // Arrange
        Pauta pauta = PautaHelperTest.criarPauta();

        when(pautaRepository.listarTodas()).thenReturn(List.of(pauta));
        when(pautaMapper.toResponse(any(Pauta.class))).thenReturn(new PautaResponse());

        // Act
        List<PautaResponse> pautas = pautaService.listarTodas();

        // Assert
        assertFalse(pautas.isEmpty());
    }

    @Test
    void deveBuscarPautaPorIdComSucesso() {
        // Arrange
        Pauta pauta = PautaHelperTest.criarPauta();
        UUID id = pauta.getId();

        when(pautaRepository.buscarPorId(id)).thenReturn(Optional.of(pauta));
        when(pautaMapper.toResponse(any(Pauta.class))).thenReturn(new PautaResponse());

        // Act
        PautaResponse response = pautaService.buscarPorId(id);

        // Assert
        assertNotNull(response);
    }

    @Test
    void deveLancarErroQuandoPautaNaoEncontrada() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(pautaRepository.buscarPorId(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntidadeNaoEncontradaException.class, () -> pautaService.buscarPorId(id));
    }

    @Test
    void deveAtualizarStatusDaPauta() {
        // Arrange
        UUID id = UUID.randomUUID();
        StatusPauta novoStatus = StatusPauta.APROVADA;

        // Act
        pautaService.atualizarStatus(id, novoStatus);

        // Assert
        verify(pautaRepository, times(1)).atualizarStatus(id, novoStatus.name());
    }
}
