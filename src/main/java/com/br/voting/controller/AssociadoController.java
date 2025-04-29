
package com.br.voting.controller;

import com.br.voting.dto.AssociadoRequest;
import com.br.voting.dto.AssociadoResponse;
import com.br.voting.service.AssociadoService;
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
@RequestMapping("/associados")
@RequiredArgsConstructor
public class AssociadoController {

    private final AssociadoService associadoService;

    @Operation(summary = "Cadastra um novo associado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Associado criado com sucesso")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrarAssociado(@Valid @RequestBody AssociadoRequest associadoRequest) {
        associadoService.criar(associadoRequest);
    }

    @Operation(summary = "Lista todos os associados cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    @GetMapping
    public List<AssociadoResponse> listarAssociados() {
        return associadoService.listarTodos();
    }

    @Operation(summary = "Busca um associado por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Associado encontrado com sucesso")
    })
    @GetMapping("/{id}")
    public AssociadoResponse buscarAssociadoPorId(@PathVariable UUID id) {
        return associadoService.buscarPorId(id);
    }

    @Operation(summary = "Deleta um associado por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Associado deletado com sucesso")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desativarAssociado(@PathVariable UUID id) {
        associadoService.desativar(id);
    }
}
