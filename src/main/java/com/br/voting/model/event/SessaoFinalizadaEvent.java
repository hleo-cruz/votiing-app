package com.br.voting.model.event;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class SessaoFinalizadaEvent {
    private UUID idSessao;
    private UUID idPauta;
    private LocalDateTime dataHoraFim;
    private ResultadoVotacao resultado;
}