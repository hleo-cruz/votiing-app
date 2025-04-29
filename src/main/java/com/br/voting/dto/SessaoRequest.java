package com.br.voting.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class SessaoRequest {

    @NotNull(message = "O ID da pauta é obrigatório.")
    private UUID idPauta;

    @NotNull(message = "A data e hora de início da sessão são obrigatórias.")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataHoraInicio;

    @NotNull(message = "A data e hora de fim da sessão são obrigatórias.")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataHoraFim;
}
