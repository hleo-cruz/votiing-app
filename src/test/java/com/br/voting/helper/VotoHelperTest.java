package com.br.voting.helper;

import com.br.voting.dto.VotoRequest;
import com.br.voting.dto.VotoResponse;
import com.br.voting.enums.EscolhaVoto;
import com.br.voting.model.Voto;
import java.util.UUID;

public class VotoHelperTest {

    public static VotoRequest criarVotoRequest() {
        VotoRequest request = new VotoRequest();
        request.setIdAssociado(UUID.randomUUID());
        request.setIdSessao(UUID.randomUUID());
        request.setEscolha(EscolhaVoto.SIM);
        return request;
    }

    public static Voto criarVoto() {
        Voto voto = new Voto();
        voto.setId(UUID.randomUUID());
        voto.setIdAssociado(UUID.randomUUID());
        voto.setIdSessao(UUID.randomUUID());
        voto.setEscolha(EscolhaVoto.SIM);
        return voto;
    }

    public static VotoResponse criarVotoReponseFromModel(Voto voto) {
        VotoResponse response = new VotoResponse();
        response.setId(voto.getId());
        response.setIdAssociado(voto.getIdAssociado());
        response.setIdSessao(voto.getIdSessao());
        response.setEscolhaVoto(voto.getEscolha());
        return response;
    }
}