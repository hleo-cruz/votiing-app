package com.br.voting.mapper;

import com.br.voting.dto.VotoRequest;
import com.br.voting.dto.VotoResponse;
import com.br.voting.model.Voto;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VotoMapper {
    public Voto toModel(VotoRequest request) {
        Voto voto = new Voto();
        voto.setId(UUID.randomUUID());
        voto.setIdAssociado(request.getIdAssociado());
        voto.setIdSessao(request.getIdSessao());
        voto.setEscolha(request.getEscolha());
        return voto;
    }

    public VotoResponse toResponse(Voto voto) {
        VotoResponse response = new VotoResponse();
        response.setId(voto.getId());
        response.setIdAssociado(voto.getIdAssociado());
        response.setIdSessao(voto.getIdSessao());
        response.setEscolhaVoto(voto.getEscolha());
        return response;
    }
}
