package com.br.voting.dto;

import com.br.voting.enums.EscolhaVoto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.UUID;

@Data
public class VotoRequest {
    @NotNull(message = "O ID do associado é obrigatório.")
    private UUID idAssociado;
    @NotNull(message = "O ID da sessão é obrigatório.")
    private UUID idSessao;
    @NotNull(message = "A escolha do voto é obrigatória (SIM ou NAO).")
    private EscolhaVoto escolha;
}
