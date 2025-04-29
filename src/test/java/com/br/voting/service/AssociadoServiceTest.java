package com.br.voting.service;

import com.br.voting.config.TestMockConfig;
import com.br.voting.dto.AssociadoRequest;
import com.br.voting.dto.AssociadoResponse;
import com.br.voting.enums.StatusAssociado;
import com.br.voting.helper.AssociadoHelperTest;
import com.br.voting.mapper.AssociadoMapper;
import com.br.voting.model.Associado;
import com.br.voting.repository.AssociadoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest(properties = {"spring.profiles.active=test"})
@Import(TestMockConfig.class)
class AssociadoServiceTest {

    @Autowired
    private AssociadoService associadoService;

    @Autowired
    private AssociadoRepository associadoRepository;

    @Autowired
    private AssociadoMapper associadoMapper;

    @Test
    void deveCriarAssociadoComSucesso() {
        // Arrange
        AssociadoRequest request = AssociadoHelperTest.criarAssociadoRequest();
        Associado associado = AssociadoHelperTest.criarAssociado();
        AssociadoResponse response = AssociadoHelperTest.criarAssociadoResponseFromModel(associado);

        when(associadoMapper.toModel(request)).thenReturn(associado);
        when(associadoMapper.toResponse(associado)).thenReturn(response);


        // Act
        AssociadoResponse result = associadoService.criar(request);

        // Assert
        assertNotNull(result);
        verify(associadoRepository, times(1)).salvar(associado);
    }

    @Test
    void deveListarTodosOsAssociados() {
        // Arrange
        Associado associado = AssociadoHelperTest.criarAssociado();

        when(associadoRepository.listarTodos()).thenReturn(List.of(associado));
        when(associadoMapper.toResponse(any(Associado.class))).thenReturn(new AssociadoResponse());

        // Act
        List<AssociadoResponse> associados = associadoService.listarTodos();

        // Assert
        assertFalse(associados.isEmpty());
        verify(associadoRepository, times(1)).listarTodos();
    }

    @Test
    void deveBuscarAssociadoPorIdComSucesso() {
        // Arrange
        UUID id = UUID.randomUUID();
        Associado associado = AssociadoHelperTest.criarAssociado();

        when(associadoRepository.buscarPorId(id)).thenReturn(Optional.of(associado));
        when(associadoMapper.toResponse(any(Associado.class))).thenReturn(new AssociadoResponse());

        // Act
        AssociadoResponse response = associadoService.buscarPorId(id);

        // Assert
        assertNotNull(response);
        verify(associadoRepository, times(1)).buscarPorId(id);
    }

    @Test
    void deveDesativarAssociadoComSucesso() {
        // Arrange
        UUID id = UUID.randomUUID();
        Associado associado = AssociadoHelperTest.criarAssociado();
        associado.setStatus(StatusAssociado.ATIVO);

        when(associadoRepository.buscarPorId(id)).thenReturn(Optional.of(associado));

        // Act
        associadoService.desativar(id);

        // Assert
        verify(associadoRepository, times(1)).desativar(id);
    }
}
