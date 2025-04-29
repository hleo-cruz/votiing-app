package com.br.voting.model;

import com.br.voting.enums.StatusPauta;
import lombok.Data;

import java.util.UUID;

@Data
public class Pauta {
    private UUID id;
    private String titulo;
    private String descricao;
    private StatusPauta status;
}
