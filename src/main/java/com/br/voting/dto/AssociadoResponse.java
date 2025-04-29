package com.br.voting.dto;

import com.br.voting.enums.StatusAssociado;
import lombok.Data;

import java.util.UUID;

@Data
public class AssociadoResponse {
    private UUID id;
    private String nome;
    private String cpf;
    private StatusAssociado status;
}
