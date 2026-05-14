package com.desafio.reservas.controllers;

import com.desafio.reservas.application.dtos.ConsultarSalasLivresRequest;
import com.desafio.reservas.application.dtos.CriarReservaRequest;
import com.desafio.reservas.application.dtos.ReservaResponse;
import com.desafio.reservas.application.dtos.SalaResponse;
import com.desafio.reservas.application.usecase.ReservaUseCase;
import com.desafio.reservas.domain.model.Reserva;
import com.desafio.reservas.domain.model.Sala;
import com.desafio.reservas.infrastructure.mappers.ReservaMapper;
import com.desafio.reservas.infrastructure.mappers.SalaMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reservas")
@Tag(name = "Reservas", description = "Gerenciamento de reservas de salas e auditórios")
public class ReservaController {

    private final ReservaUseCase reservaUseCase;
    private final ReservaMapper reservaMapper;
    private final SalaMapper salaMapper;

    public ReservaController(ReservaUseCase reservaUseCase,
                             ReservaMapper reservaMapper,
                             SalaMapper salaMapper) {
        this.reservaUseCase = reservaUseCase;
        this.reservaMapper = reservaMapper;
        this.salaMapper = salaMapper;
    }

    @Operation(summary = "Criar reserva", description = "Realiza uma nova reserva para uma sala em um dia e horário específicos")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reserva criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou horário incoerente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Sala não encontrada", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflito de horário com outra reserva ativa", content = @Content),
            @ApiResponse(responseCode = "422", description = "Sala inativa", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ReservaResponse> criar(@RequestBody @Valid CriarReservaRequest request) {
        Reserva reserva = reservaUseCase.criarReserva(
                request.getSalaId(),
                request.getResponsavel(),
                request.getInicio(),
                request.getFim()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaMapper.toResponse(reserva));
    }

    @Operation(summary = "Buscar reserva por ID", description = "Retorna os detalhes de uma reserva pelo seu identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva encontrada"),
            @ApiResponse(responseCode = "404", description = "Reserva não encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponse> buscarPorId(
            @Parameter(description = "ID da reserva") @PathVariable Long id) {
        Reserva reserva = reservaUseCase.buscarPorId(id);
        return ResponseEntity.ok(reservaMapper.toResponse(reserva));
    }

    @Operation(summary = "Cancelar reserva", description = "Cancela uma reserva ativa. Reservas já canceladas não podem ser canceladas novamente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva cancelada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Reserva não encontrada", content = @Content),
            @ApiResponse(responseCode = "422", description = "Reserva já está cancelada", content = @Content)
    })
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<ReservaResponse> cancelar(
            @Parameter(description = "ID da reserva") @PathVariable Long id) {
        Reserva reserva = reservaUseCase.cancelarReserva(id);
        return ResponseEntity.ok(reservaMapper.toResponse(reserva));
    }

    @Operation(summary = "Consultar agenda diária", description = "Lista todas as reservas ativas de todas as salas em uma determinada data")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agenda retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Data inválida", content = @Content)
    })
    @GetMapping("/agenda")
    public ResponseEntity<List<ReservaResponse>> agendaDiaria(
            @Parameter(description = "Data no formato yyyy-MM-dd")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {

        List<Reserva> reservas = reservaUseCase.consultarAgendaDiaria(data);
        return ResponseEntity.ok(reservaMapper.toResponseList(reservas));
    }

    @Operation(summary = "Consultar agenda por sala e dia", description = "Lista todas as reservas ativas de uma sala específica em uma data")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agenda retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Sala não encontrada", content = @Content)
    })
    @GetMapping("/agenda/sala/{salaId}")
    public ResponseEntity<List<ReservaResponse>> agendaPorSalaEDia(
            @Parameter(description = "ID da sala") @PathVariable Long salaId,
            @Parameter(description = "Data no formato yyyy-MM-dd")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {

        List<Reserva> reservas = reservaUseCase.consultarAgendaPorSalaEDia(salaId, data);
        return ResponseEntity.ok(reservaMapper.toResponseList(reservas));
    }

    @Operation(summary = "Listar reservas por responsável", description = "Retorna todas as reservas vinculadas a um determinado responsável")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping("/responsavel/{responsavel}")
    public ResponseEntity<List<ReservaResponse>> listarPorResponsavel(
            @Parameter(description = "Nome do responsável") @PathVariable String responsavel) {

        List<Reserva> reservas = reservaUseCase.listarPorResponsavel(responsavel);
        return ResponseEntity.ok(reservaMapper.toResponseList(reservas));
    }

    @Operation(summary = "Consultar salas livres", description = "Retorna todas as salas ativas sem conflito de reserva para o intervalo de horário informado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de salas disponíveis retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou horário incoerente", content = @Content)
    })
    @PostMapping("/salas-livres")
    public ResponseEntity<List<SalaResponse>> consultarSalasLivres(
            @RequestBody @Valid ConsultarSalasLivresRequest request) {

        List<Sala> salas = reservaUseCase.consultarSalasLivres(
                request.getInicio().toLocalDate(),
                request.getInicio(),
                request.getFim()
        );
        return ResponseEntity.ok(salaMapper.toResponseList(salas));
    }
}