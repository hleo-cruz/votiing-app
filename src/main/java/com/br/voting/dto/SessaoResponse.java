package com.br.voting.dto;

import lombok.Data;
import java.util.UUID;
import java.time.LocalDateTime;

@Data
public class SessaoResponse {
    private UUID id;
    private UUID idPauta;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
}
