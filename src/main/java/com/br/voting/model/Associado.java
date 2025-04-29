package com.br.voting.model;

import com.br.voting.enums.StatusAssociado;
import lombok.Data;

import java.util.UUID;

@Data
public class Associado {
    private UUID id;
    private String nome;
    private String cpf;
    private StatusAssociado status;
}
