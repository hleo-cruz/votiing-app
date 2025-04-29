
package com.br.voting.controller;

import com.br.voting.dto.PautaRequest;
import com.br.voting.dto.PautaResponse;
import com.br.voting.enums.StatusPauta;
import com.br.voting.service.PautaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pautas")
@RequiredArgsConstructor
public class PautaController {

    private final PautaService pautaService;

    @Operation(summary = "Cadastra uma nova pauta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pauta criada com sucesso")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrarPauta(@Valid @RequestBody PautaRequest pautaRequest) {
        pautaService.criar(pautaRequest);
    }

    @Operation(summary = "Lista todas as pautas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pautas retornada com sucesso")
    })
    @GetMapping
    public List<PautaResponse> listarPautas() {
        return pautaService.listarTodas();
    }

    @Operation(summary = "Busca uma pauta por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pauta encontrada com sucesso")
    })
    @GetMapping("/{id}")
    public PautaResponse buscarPautaPorId(@PathVariable UUID id) {
        return pautaService.buscarPorId(id);
    }

    @Operation(summary = "Deleta uma pauta por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pauta deletada com sucesso")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarPauta(@PathVariable UUID id) {
        pautaService.atualizarStatus(id, StatusPauta.CANCELADA);
    }
}
