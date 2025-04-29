package com.br.voting.model.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultadoVotacao {

    private Long votosSim;
    private Long votosNao;
    private ResultadoFinal resultado;

}