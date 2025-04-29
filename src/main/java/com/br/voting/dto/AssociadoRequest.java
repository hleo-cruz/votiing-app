package com.br.voting.dto;

import com.br.voting.enums.StatusAssociado;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssociadoRequest {

    @NotBlank(message = "O nome do associado é obrigatório.")
    private String nome;

    @NotBlank(message = "O CPF do associado é obrigatório.")
    private String cpf;

}
