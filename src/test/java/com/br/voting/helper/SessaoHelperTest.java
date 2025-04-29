package com.br.voting.helper;

import com.br.voting.dto.SessaoRequest;
import com.br.voting.dto.SessaoResponse;
import com.br.voting.enums.StatusSessao;
import com.br.voting.model.Sessao;
import java.time.LocalDateTime;
import java.util.UUID;

public class SessaoHelperTest {

    public static SessaoRequest criarSessaoRequest() {
        SessaoRequest request = new SessaoRequest();
        request.setIdPauta(UUID.randomUUID());
        request.setDataHoraInicio(LocalDateTime.now());
        request.setDataHoraFim(LocalDateTime.now().plusMinutes(5));
        return request;
    }

    public static Sessao criarSessao() {
        Sessao sessao = new Sessao();
        sessao.setId(UUID.randomUUID());
        sessao.setIdPauta(UUID.randomUUID());
        sessao.setDataHoraInicio(LocalDateTime.now());
        sessao.setDataHoraFim(LocalDateTime.now().plusMinutes(5));
        sessao.setStatus(StatusSessao.ABERTA);
        return sessao;
    }

    public static SessaoResponse criarSessaoResponseFromModel(Sessao sessao) {
        SessaoResponse response = new SessaoResponse();
        response.setId(sessao.getId());
        response.setIdPauta(sessao.getIdPauta());
        response.setDataHoraInicio(sessao.getDataHoraInicio());
        response.setDataHoraFim(sessao.getDataHoraFim());
        return response;
    }
}