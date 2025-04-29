package com.br.voting.service;

import com.br.voting.dto.SessaoRequest;
import com.br.voting.dto.SessaoResponse;
import com.br.voting.enums.StatusSessao;
import com.br.voting.exception.EntidadeNaoEncontradaException;
import com.br.voting.exception.ErroNegocioException;
import com.br.voting.mapper.SessaoMapper;
import com.br.voting.model.Sessao;
import com.br.voting.repository.PautaRepository;
import com.br.voting.repository.SessaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessaoService {

    private final SessaoRepository sessaoRepository;
    private final PautaRepository pautaRepository;
    private final SessaoMapper sessaoMapper;
    private final MensagemService mensagemService;

    public SessaoResponse criar(SessaoRequest request) {
        try {
            log.info("CRIAR_SESSAO - INICIO - Criando sessão - idPauta={}", request.getIdPauta());

            pautaRepository.buscarPorId(request.getIdPauta())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException(mensagemService.getMensagem("entidade.pauta.nao-encontrada")));

            Sessao sessao = sessaoMapper.toModel(request);
            if (sessao.getDataHoraInicio().isAfter(LocalDateTime.now())) {
                sessao.setStatus(StatusSessao.AGUARDANDO);
            } else {
                sessao.setStatus(StatusSessao.ABERTA);
            }
            sessaoRepository.salvar(sessao);

            log.info("CRIAR_SESSAO - SUCESSO - Sessão criada - id={}", sessao.getId());
            return sessaoMapper.toResponse(sessao);
        } catch (Exception e) {
            log.error("CRIAR_SESSAO - ERRO - Falha ao criar sessão - message={}", e.getMessage(), e);
            throw new ErroNegocioException(mensagemService.getMensagem("erro.negocio"));
        }
    }

    public List<SessaoResponse> listarTodas() {
        try {
            log.info("LISTAR_SESSOES - INICIO - Buscando todas as sessões.");
            List<SessaoResponse> sessoes = sessaoRepository.listarTodas().stream()
                    .map(sessaoMapper::toResponse)
                    .collect(Collectors.toList());
            log.info("LISTAR_SESSOES - SUCESSO - Total encontradas={}", sessoes.size());
            return sessoes;
        } catch (Exception e) {
            log.error("LISTAR_SESSOES - ERRO - Falha ao listar sessões - message={}", e.getMessage(), e);
            throw new ErroNegocioException(mensagemService.getMensagem("erro.negocio"));
        }
    }

    public SessaoResponse buscarPorId(UUID id) {
        try {
            log.info("BUSCAR_SESSAO - INICIO - Buscando sessão - id={}", id);
            Sessao sessao = sessaoRepository.buscarPorId(id)
                    .orElseThrow(() -> new EntidadeNaoEncontradaException(mensagemService.getMensagem("entidade.sessao.nao-encontrada")));
            log.info("BUSCAR_SESSAO - SUCESSO - Sessão encontrada - id={}", id);
            return sessaoMapper.toResponse(sessao);
        } catch (Exception e) {
            log.error("BUSCAR_SESSAO - ERRO - Falha ao buscar sessão - id={}, message={}", id, e.getMessage(), e);
            throw new ErroNegocioException(mensagemService.getMensagem("erro.negocio"));
        }
    }

    public void deletar(UUID id) {
        try {
            log.info("DELETAR_SESSAO - INICIO - Deletando sessão - id={}", id);
            sessaoRepository.buscarPorId(id)
                    .orElseThrow(() -> new EntidadeNaoEncontradaException(mensagemService.getMensagem("entidade.sessao.nao-encontrada")));
            sessaoRepository.finalizarSessao(id);
            log.info("DELETAR_SESSAO - SUCESSO - Sessão deletada - id={}", id);
        } catch (Exception e) {
            log.error("DELETAR_SESSAO - ERRO - Falha ao deletar sessão - id={}, message={}", id, e.getMessage(), e);
            throw new ErroNegocioException(mensagemService.getMensagem("erro.negocio"));
        }
    }

    public void abrirSessoesAgendadas() {
        try {
            log.info("ABRIR_SESSOES_AGENDADAS - INICIO - Verificando abertura de sessões.");
            sessaoRepository.abrirSessoesAgendadas();
            log.info("ABRIR_SESSOES_AGENDADAS - SUCESSO - Sessões abertas com sucesso.");
        } catch (Exception e) {
            log.error("ABRIR_SESSOES_AGENDADAS - ERRO - Falha ao abrir sessões - message={}", e.getMessage(), e);
            throw new ErroNegocioException(mensagemService.getMensagem("erro.negocio"));
        }
    }


    public List<Sessao> listarSessoesExpiradas() {
        try {
            log.info("LISTAR_SESSOES_EXPIRADAS - INICIO");
            List<Sessao> expiradas = sessaoRepository.listarSessoesExpiradas();
            log.info("LISTAR_SESSOES_EXPIRADAS - SUCESSO - total={}", expiradas.size());
            return expiradas;
        } catch (Exception e) {
            log.error("LISTAR_SESSOES_EXPIRADAS - ERRO - message={}", e.getMessage(), e);
            throw new ErroNegocioException(mensagemService.getMensagem("erro.negocio"));
        }
    }

    public void finalizarSessao(UUID id) {
        try {
            log.info("FINALIZAR_SESSAO - INICIO - id={}", id);
            sessaoRepository.finalizarSessao(id);
            log.info("FINALIZAR_SESSAO - SUCESSO - id={}", id);
        } catch (Exception e) {
            log.error("FINALIZAR_SESSAO - ERRO - id={}, message={}", id, e.getMessage(), e);
            throw new ErroNegocioException(mensagemService.getMensagem("erro.negocio"));
        }
    }

}
