package com.br.voting.service;

import com.br.voting.dto.AssociadoRequest;
import com.br.voting.dto.AssociadoResponse;
import com.br.voting.exception.EntidadeNaoEncontradaException;
import com.br.voting.exception.ErroNegocioException;
import com.br.voting.mapper.AssociadoMapper;
import com.br.voting.model.Associado;
import com.br.voting.repository.AssociadoRepository;
import com.br.voting.util.CpfUtils;
import com.br.voting.util.LogUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssociadoService {

    private final AssociadoRepository associadoRepository;
    private final AssociadoMapper associadoMapper;
    private final MensagemService mensagemService;

    public AssociadoResponse criar(AssociadoRequest request) {
        try {
            log.info("CRIAR_ASSOCIADO - INICIO - Criando associado - nome={}, cpf={}", request.getNome(), LogUtils.mascararCpf(request.getCpf()));

            if (!CpfUtils.isValidCpf(request.getCpf())) {
                log.error("CRIAR_ASSOCIADO - ERRO - CPF inválido - cpf={}", LogUtils.mascararCpf(request.getCpf()));
                throw new ErroNegocioException(mensagemService.getMensagem("erro.negocio.cpf-invalido"));
            }

            Associado associado = associadoMapper.toModel(request);
            associadoRepository.salvar(associado);

            log.info("CRIAR_ASSOCIADO - SUCESSO - Associado criado - id={}", associado.getId());
            return associadoMapper.toResponse(associado);
        } catch (Exception e) {
            log.error("CRIAR_ASSOCIADO - ERRO - Falha ao criar associado - message={}", e.getMessage(), e);
            throw new ErroNegocioException(mensagemService.getMensagem("erro.negocio"));
        }
    }

    public List<AssociadoResponse> listarTodos() {
        try {
            log.info("LISTAR_ASSOCIADOS - INICIO - Buscando todos os associados.");
            List<AssociadoResponse> associados = associadoRepository.listarTodos().stream()
                    .map(associadoMapper::toResponse)
                    .collect(Collectors.toList());
            log.info("LISTAR_ASSOCIADOS - SUCESSO - Total encontrados={}", associados.size());
            return associados;
        } catch (Exception e) {
            log.error("LISTAR_ASSOCIADOS - ERRO - Falha ao listar associados - message={}", e.getMessage(), e);
            throw new ErroNegocioException(mensagemService.getMensagem("erro.negocio"));
        }
    }

    public AssociadoResponse buscarPorId(UUID id) {
        try {
            log.info("BUSCAR_ASSOCIADO - INICIO - Buscando associado - id={}", id);
            Associado associado = associadoRepository.buscarPorId(id)
                    .orElseThrow(() -> new EntidadeNaoEncontradaException(mensagemService.getMensagem("entidade.associado.nao-encontrado")));
            log.info("BUSCAR_ASSOCIADO - SUCESSO - Associado encontrado - id={}", id);
            return associadoMapper.toResponse(associado);
        } catch (Exception e) {
            log.error("BUSCAR_ASSOCIADO - ERRO - Falha ao buscar associado - id={}, message={}", id, e.getMessage(), e);
            throw new ErroNegocioException(mensagemService.getMensagem("erro.negocio"));
        }
    }

    public void desativar(UUID id) {
        try {
            log.info("DELETAR_ASSOCIADO - INICIO - Deletando associado - id={}", id);
            associadoRepository.buscarPorId(id)
                    .orElseThrow(() -> new EntidadeNaoEncontradaException(mensagemService.getMensagem("entidade.associado.nao-encontrado")));
            associadoRepository.desativar(id);
            log.info("DELETAR_ASSOCIADO - SUCESSO - Associado deletado - id={}", id);
        } catch (Exception e) {
            log.error("DELETAR_ASSOCIADO - ERRO - Falha ao deletar associado - id={}, message={}", id, e.getMessage(), e);
            throw new ErroNegocioException(mensagemService.getMensagem("erro.negocio"));
        }
    }
}
