
package com.br.voting.controller;

import com.br.voting.dto.VotoRequest;
import com.br.voting.dto.VotoResponse;
import com.br.voting.service.VotoService;
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
@RequestMapping("/votos")
@RequiredArgsConstructor
public class VotoController {

    private final VotoService votoService;

    @Operation(summary = "Registra um novo voto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Voto registrado com sucesso")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void votar(@Valid @RequestBody VotoRequest votoRequest) {
        votoService.criar(votoRequest);
    }

    @Operation(summary = "Lista todos os votos registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de votos retornada com sucesso")
    })
    @GetMapping
    public List<VotoResponse> listarVotos() {
        return votoService.listarTodos();
    }

    @Operation(summary = "Busca um voto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Voto encontrado com sucesso")
    })
    @GetMapping("/{id}")
    public VotoResponse buscarVotoPorId(@PathVariable UUID id) {
        return votoService.buscarPorId(id);
    }

}
