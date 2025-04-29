package com.br.voting.dto;

import com.br.voting.enums.EscolhaVoto;
import lombok.Data;
import java.util.UUID;

@Data
public class VotoResponse {
    private UUID id;
    private UUID idAssociado;
    private UUID idSessao;
    private EscolhaVoto escolhaVoto;
}
