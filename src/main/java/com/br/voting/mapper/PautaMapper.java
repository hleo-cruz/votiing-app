package com.br.voting.mapper;

import com.br.voting.dto.PautaRequest;
import com.br.voting.dto.PautaResponse;
import com.br.voting.enums.StatusPauta;
import com.br.voting.model.Pauta;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PautaMapper {
    public Pauta toModel(PautaRequest request) {
        Pauta pauta = new Pauta();
        pauta.setId(UUID.randomUUID());
        pauta.setTitulo(request.getTitulo());
        pauta.setDescricao(request.getDescricao());
        pauta.setStatus(StatusPauta.PENDENTE);
        return pauta;
    }

    public PautaResponse toResponse(Pauta pauta) {
        PautaResponse response = new PautaResponse();
        response.setId(pauta.getId());
        response.setTitulo(pauta.getTitulo());
        response.setDescricao(pauta.getDescricao());
        response.setStatus(pauta.getStatus());
        return response;
    }
}
