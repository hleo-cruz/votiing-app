package com.br.voting.service;

import com.br.voting.dto.VotoRequest;
import com.br.voting.dto.VotoResponse;
import com.br.voting.exception.EntidadeNaoEncontradaException;
import com.br.voting.exception.ErroNegocioException;
import com.br.voting.mapper.VotoMapper;
import com.br.voting.model.Voto;
import com.br.voting.model.event.ResultadoFinal;
import com.br.voting.model.event.ResultadoVotacao;
import com.br.voting.repository.AssociadoRepository;
import com.br.voting.repository.SessaoRepository;
import com.br.voting.repository.VotoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class VotoService {

    private final VotoRepository votoRepository;
    private final AssociadoRepository associadoRepository;
    private final SessaoRepository sessaoRepository;
    private final VotoMapper votoMapper;
    private final MensagemService mensagemService;

    public VotoResponse criar(VotoRequest request) {
        try {
            log.info("CRIAR_VOTO - INICIO - Criando voto - idAssociado={}, idSessao={}", request.getIdAssociado(), request.getIdSessao());

            associadoRepository.buscarPorId(request.getIdAssociado())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException(mensagemService.getMensagem("entidade.associado.nao-encontrado")));

            sessaoRepository.buscarPorId(request.getIdSessao())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException(mensagemService.getMensagem("entidade.sessao.nao-encontrada")));

            // Nova regra: verificar se o associado já votou na sessão
            boolean jaVotou = votoRepository.existsByIdAssociadoAndIdSessao(request.getIdAssociado(), request.getIdSessao());
            if (jaVotou) {
                log.error("CRIAR_VOTO - ERRO - Associado já votou nesta sessão - idAssociado={}, idSessao={}", request.getIdAssociado(), request.getIdSessao());
                throw new ErroNegocioException(mensagemService.getMensagem("erro.negocio.voto-duplicado"));
            }

            Voto voto = votoMapper.toModel(request);
            votoRepository.salvar(voto);

            log.info("CRIAR_VOTO - SUCESSO - Voto criado - id={}", voto.getId());
            return votoMapper.toResponse(voto);
        } catch (Exception e) {
            log.error("CRIAR_VOTO - ERRO - Falha ao criar voto - message={}", e.getMessage(), e);
            throw new ErroNegocioException(mensagemService.getMensagem("erro.negocio"));
        }
    }

    public List<VotoResponse> listarTodos() {
        try {
            log.info("LISTAR_VOTOS - INICIO - Buscando todos os votos.");
            List<VotoResponse> votos = votoRepository.listarTodos().stream()
                    .map(votoMapper::toResponse)
                    .collect(Collectors.toList());
            log.info("LISTAR_VOTOS - SUCESSO - Total encontrados={}", votos.size());
            return votos;
        } catch (Exception e) {
            log.error("LISTAR_VOTOS - ERRO - Falha ao listar votos - message={}", e.getMessage(), e);
            throw new ErroNegocioException(mensagemService.getMensagem("erro.negocio"));
        }
    }

    public VotoResponse buscarPorId(UUID id) {
        try {
            log.info("BUSCAR_VOTO - INICIO - Buscando voto - id={}", id);
            Voto voto = votoRepository.buscarPorId(id)
                    .orElseThrow(() -> new EntidadeNaoEncontradaException(mensagemService.getMensagem("entidade.voto.nao-encontrado")));
            log.info("BUSCAR_VOTO - SUCESSO - Voto encontrado - id={}", id);
            return votoMapper.toResponse(voto);
        } catch (Exception e) {
            log.error("BUSCAR_VOTO - ERRO - Falha ao buscar voto - id={}, message={}", id, e.getMessage(), e);
            throw new ErroNegocioException(mensagemService.getMensagem("erro.negocio"));
        }
    }

    public ResultadoVotacao apurarResultado(UUID idSessao) {

        List<Voto> votos = votoRepository.buscarPorIdSessao(idSessao);

        Long votosSim = votos.stream().filter(v -> v.getEscolha().name().equalsIgnoreCase("SIM")).count();
        Long votosNao = votos.stream().filter(v -> v.getEscolha().name().equalsIgnoreCase("NAO")).count();

        ResultadoFinal resultado;
        if (votosSim > votosNao) {
            resultado = ResultadoFinal.SIM;
        } else if (votosNao > votosSim) {
            resultado = ResultadoFinal.NAO;
        } else {
            resultado = ResultadoFinal.EMPATE;
        }

        return new ResultadoVotacao(votosSim, votosNao, resultado);
    }

}
