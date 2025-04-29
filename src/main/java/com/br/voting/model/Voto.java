package com.br.voting.model;

import com.br.voting.enums.EscolhaVoto;
import lombok.Data;
import java.util.UUID;

@Data
public class Voto {
    private UUID id;
    private UUID idAssociado;
    private UUID idSessao;
    private EscolhaVoto escolha;
}
