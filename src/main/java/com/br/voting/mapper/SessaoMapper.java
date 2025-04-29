package com.br.voting.mapper;

import com.br.voting.dto.SessaoRequest;
import com.br.voting.dto.SessaoResponse;
import com.br.voting.model.Sessao;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SessaoMapper {
    public Sessao toModel(SessaoRequest request) {
        Sessao sessao = new Sessao();
        sessao.setId(UUID.randomUUID());
        sessao.setIdPauta(request.getIdPauta());
        sessao.setDataHoraInicio(request.getDataHoraInicio());
        sessao.setDataHoraFim(request.getDataHoraFim());
        return sessao;
    }

    public SessaoResponse toResponse(Sessao sessao) {
        SessaoResponse response = new SessaoResponse();
        response.setId(sessao.getId());
        response.setIdPauta(sessao.getIdPauta());
        response.setDataHoraInicio(sessao.getDataHoraInicio());
        response.setDataHoraFim(sessao.getDataHoraFim());
        return response;
    }
}
