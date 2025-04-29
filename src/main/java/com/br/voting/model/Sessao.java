package com.br.voting.model;

import com.br.voting.enums.StatusSessao;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Sessao {
    private UUID id;
    private UUID idPauta;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private StatusSessao status;
}
