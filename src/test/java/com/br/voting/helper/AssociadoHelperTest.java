package com.br.voting.helper;

import com.br.voting.dto.AssociadoRequest;
import com.br.voting.dto.AssociadoResponse;
import com.br.voting.model.Associado;
import java.util.UUID;

public class AssociadoHelperTest {

    public static AssociadoRequest criarAssociadoRequest() {
        AssociadoRequest request = new AssociadoRequest();
        request.setNome("Associado Teste");
        request.setCpf("06376665004");
        return request;
    }

    public static Associado criarAssociado() {
        Associado associado = new Associado();
        associado.setId(UUID.randomUUID());
        associado.setNome("Associado Teste");
        associado.setCpf("06376665004");
        return associado;
    }

    public static AssociadoResponse criarAssociadoResponseFromModel(Associado associado) {
        AssociadoResponse response = new AssociadoResponse();
        response.setId(UUID.randomUUID());
        response.setNome(associado.getNome());
        response.setCpf(associado.getCpf());
        response.setStatus(associado.getStatus());
        return response;
    }
}