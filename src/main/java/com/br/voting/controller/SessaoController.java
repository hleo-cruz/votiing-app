
package com.br.voting.controller;

import com.br.voting.dto.SessaoRequest;
import com.br.voting.dto.SessaoResponse;
import com.br.voting.service.SessaoService;
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
@RequestMapping("/sessoes")
@RequiredArgsConstructor
public class SessaoController {

    private final SessaoService sessaoService;

    @Operation(summary = "Abre uma nova sessão de votação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sessão criada com sucesso")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void abrirSessao(@Valid @RequestBody SessaoRequest sessaoRequest) {
        sessaoService.criar(sessaoRequest);
    }

    @Operation(summary = "Lista todas as sessões de votação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de sessões retornada com sucesso")
    })
    @GetMapping
    public List<SessaoResponse> listarSessoes() {
        return sessaoService.listarTodas();
    }

    @Operation(summary = "Busca uma sessão por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sessão encontrada com sucesso")
    })
    @GetMapping("/{id}")
    public SessaoResponse buscarSessaoPorId(@PathVariable UUID id) {
        return sessaoService.buscarPorId(id);
    }

    @Operation(summary = "Deleta uma sessão por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Sessão deletada com sucesso")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarSessao(@PathVariable UUID id) {
        sessaoService.deletar(id);
    }
}
