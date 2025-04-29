package com.br.voting.service;

import com.br.voting.dto.PautaRequest;
import com.br.voting.dto.PautaResponse;
import com.br.voting.enums.StatusPauta;
import com.br.voting.exception.EntidadeNaoEncontradaException;
import com.br.voting.exception.ErroNegocioException;
import com.br.voting.mapper.PautaMapper;
import com.br.voting.model.Pauta;
import com.br.voting.repository.PautaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PautaService {

    private final PautaRepository pautaRepository;
    private final PautaMapper pautaMapper;
    private final MensagemService mensagemService;

    public PautaResponse criar(PautaRequest request) {
        try {
            log.info("CRIAR_PAUTA - INICIO - Criando pauta - titulo={}", request.getTitulo());
            Pauta pauta = pautaMapper.toModel(request);
            pautaRepository.salvar(pauta);
            log.info("CRIAR_PAUTA - SUCESSO - Pauta criada - id={}", pauta.getId());
            return pautaMapper.toResponse(pauta);
        } catch (Exception e) {
            log.error("CRIAR_PAUTA - ERRO - Falha ao criar pauta - message={}", e.getMessage(), e);
            throw new ErroNegocioException(mensagemService.getMensagem("erro.negocio"));
        }
    }

    public List<PautaResponse> listarTodas() {
        try {
            log.info("LISTAR_PAUTAS - INICIO - Buscando todas as pautas.");
            List<PautaResponse> pautas = pautaRepository.listarTodas().stream()
                    .map(pautaMapper::toResponse)
                    .collect(Collectors.toList());
            log.info("LISTAR_PAUTAS - SUCESSO - Total encontradas={}", pautas.size());
            return pautas;
        } catch (Exception e) {
            log.error("LISTAR_PAUTAS - ERRO - Falha ao listar pautas - message={}", e.getMessage(), e);
            throw new ErroNegocioException(mensagemService.getMensagem("erro.negocio"));
        }
    }

    public PautaResponse buscarPorId(UUID id) {
        try {
            log.info("BUSCAR_PAUTA - INICIO - Buscando pauta - id={}", id);
            Pauta pauta = pautaRepository.buscarPorId(id)
                    .orElseThrow(() -> new EntidadeNaoEncontradaException(mensagemService.getMensagem("entidade.pauta.nao-encontrada")));
            log.info("BUSCAR_PAUTA - SUCESSO - Pauta encontrada - id={}", id);
            return pautaMapper.toResponse(pauta);
        } catch (EntidadeNaoEncontradaException exception) {
            throw exception;
        } catch (Exception e) {
            log.error("BUSCAR_PAUTA - ERRO - Falha ao buscar pauta - id={}, message={}", id, e.getMessage(), e);
            throw new ErroNegocioException(mensagemService.getMensagem("erro.negocio"));
        }
    }

    public void atualizarStatus(UUID idPauta, StatusPauta status) {
        try {
            log.info("ATUALIZAR_STATUS_PAUTA - INICIO - Atualizando status - id={}, novoStatus={}", idPauta, status);
            pautaRepository.atualizarStatus(idPauta, status.name());
            log.info("ATUALIZAR_STATUS_PAUTA - SUCESSO - Status atualizado - id={}, status={}", idPauta, status);
        } catch (Exception e) {
            log.error("ATUALIZAR_STATUS_PAUTA - ERRO - Falha ao atualizar status da pauta - id={}, message={}", idPauta, e.getMessage(), e);
            throw new ErroNegocioException(mensagemService.getMensagem("erro.negocio"));
        }
    }

}
