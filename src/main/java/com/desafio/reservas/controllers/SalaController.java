package com.desafio.reservas.controllers;

import com.desafio.reservas.application.dtos.CriarSalaRequest;
import com.desafio.reservas.application.dtos.SalaResponse;
import com.desafio.reservas.application.usecase.SalaUseCase;
import com.desafio.reservas.domain.model.Sala;
import com.desafio.reservas.domain.enums.TipoSala;
import com.desafio.reservas.infrastructure.mappers.SalaMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/salas")
@Tag(name = "Salas", description = "Gerenciamento de salas de reunião e auditórios")
public class SalaController {

    private final SalaUseCase salaUseCase;
    private final SalaMapper salaMapper;

    public SalaController(SalaUseCase salaUseCase, SalaMapper salaMapper) {
        this.salaUseCase = salaUseCase;
        this.salaMapper = salaMapper;
    }

    @Operation(summary = "Cadastrar sala", description = "Cria uma nova sala de reunião ou auditório")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Sala criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "409", description = "Já existe uma sala com este nome", content = @Content)
    })
    @PostMapping
    public ResponseEntity<SalaResponse> cadastrar(@RequestBody @Valid CriarSalaRequest request) {
        Sala sala = salaUseCase.cadastrarSala(
                request.getNome(),
                request.getTipo(),
                request.getCapacidade()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(salaMapper.toResponse(sala));
    }

    @Operation(summary = "Buscar sala por ID", description = "Retorna os dados de uma sala pelo seu identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sala encontrada"),
            @ApiResponse(responseCode = "404", description = "Sala não encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<SalaResponse> buscarPorId(
            @Parameter(description = "ID da sala") @PathVariable Long id) {
        Sala sala = salaUseCase.buscarPorId(id);
        return ResponseEntity.ok(salaMapper.toResponse(sala));
    }

    @Operation(summary = "Listar todas as salas", description = "Retorna todas as salas cadastradas, ativas e inativas")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<SalaResponse>> listarTodas() {
        return ResponseEntity.ok(salaMapper.toResponseList(salaUseCase.listarTodas()));
    }

    @Operation(summary = "Listar salas ativas", description = "Retorna apenas as salas que estão ativas e disponíveis para reserva")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping("/ativas")
    public ResponseEntity<List<SalaResponse>> listarAtivas() {
        return ResponseEntity.ok(salaMapper.toResponseList(salaUseCase.listarAtivas()));
    }

    @Operation(summary = "Listar salas por tipo", description = "Filtra salas pelo tipo: REUNIAO_COLETIVA, REUNIAO_INDIVIDUAL ou AUDITORIO")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Tipo inválido", content = @Content)
    })
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<SalaResponse>> listarPorTipo(
            @Parameter(description = "Tipo da sala", schema = @Schema(implementation = TipoSala.class))
            @PathVariable TipoSala tipo) {
        return ResponseEntity.ok(salaMapper.toResponseList(salaUseCase.listarPorTipo(tipo)));
    }

    @Operation(summary = "Ativar sala", description = "Reativa uma sala que estava inativa, permitindo novas reservas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sala ativada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Sala não encontrada", content = @Content),
            @ApiResponse(responseCode = "422", description = "Sala já está ativa", content = @Content)
    })
    @PatchMapping("/{id}/ativar")
    public ResponseEntity<SalaResponse> ativar(
            @Parameter(description = "ID da sala") @PathVariable Long id) {
        Sala sala = salaUseCase.ativarSala(id);
        return ResponseEntity.ok(salaMapper.toResponse(sala));
    }

    @Operation(summary = "Desativar sala", description = "Desativa uma sala, impedindo novas reservas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sala desativada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Sala não encontrada", content = @Content),
            @ApiResponse(responseCode = "422", description = "Sala já está inativa", content = @Content)
    })
    @PatchMapping("/{id}/desativar")
    public ResponseEntity<SalaResponse> desativar(
            @Parameter(description = "ID da sala") @PathVariable Long id) {
        Sala sala = salaUseCase.desativarSala(id);
        return ResponseEntity.ok(salaMapper.toResponse(sala));
    }
}