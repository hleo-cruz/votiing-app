package com.br.voting.dto;

import com.br.voting.enums.StatusPauta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PautaRequest {

    @NotBlank(message = "O título da pauta é obrigatório.")
    private String titulo;

    @NotBlank(message = "A descrição da pauta é obrigatória.")
    private String descricao;

}
