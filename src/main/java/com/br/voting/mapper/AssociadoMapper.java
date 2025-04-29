package com.br.voting.mapper;

import com.br.voting.dto.AssociadoRequest;
import com.br.voting.dto.AssociadoResponse;
import com.br.voting.enums.StatusAssociado;
import com.br.voting.model.Associado;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AssociadoMapper {
    public Associado toModel(AssociadoRequest request) {
        Associado associado = new Associado();
        associado.setId(UUID.randomUUID());
        associado.setNome(request.getNome());
        associado.setCpf(request.getCpf());
        associado.setStatus(StatusAssociado.ATIVO);
        return associado;
    }

    public AssociadoResponse toResponse(Associado associado) {
        AssociadoResponse response = new AssociadoResponse();
        response.setId(associado.getId());
        response.setNome(associado.getNome());
        response.setCpf(associado.getCpf());
        response.setStatus(associado.getStatus());
        return response;
    }
}
