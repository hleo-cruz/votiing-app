package com.br.voting.dto;

import com.br.voting.enums.StatusPauta;
import lombok.Data;

import java.util.UUID;

@Data
public class PautaResponse {
    private UUID id;
    private String titulo;
    private String descricao;
    private StatusPauta status;
}
