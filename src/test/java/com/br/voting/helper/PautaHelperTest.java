package com.br.voting.helper;

import com.br.voting.dto.PautaRequest;
import com.br.voting.dto.PautaResponse;
import com.br.voting.model.Pauta;
import com.br.voting.enums.StatusPauta;

import java.util.UUID;

public class PautaHelperTest {

    public static PautaRequest criarPautaRequest() {
        PautaRequest request = new PautaRequest();
        request.setTitulo("Título Teste");
        request.setDescricao("Descrição Teste");
        return request;
    }

    public static Pauta criarPauta() {
        Pauta pauta = new Pauta();
        pauta.setId(UUID.randomUUID());
        pauta.setTitulo("Título Teste");
        pauta.setDescricao("Descrição Teste");
        pauta.setStatus(StatusPauta.PENDENTE);
        return pauta;
    }

    public static PautaResponse criarPautaResponseFromModel( Pauta pauta ) {
        PautaResponse response = new PautaResponse();
        response.setId(pauta.getId());
        response.setTitulo(pauta.getTitulo());
        response.setDescricao(pauta.getDescricao());
        response.setStatus(pauta.getStatus());
        return response;
    }
}
