package com.desafio.reservas.dtos;

import com.desafio.reservas.domain.enums.StatusReserva;
import com.desafio.reservas.domain.model.Reserva;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Dados de retorno de uma reserva")
public class ReservaResponseDTO {

    @Schema(description = "ID da reserva", example = "1")
    private Long id;

    @Schema(description = "ID da sala reservada", example = "1")
    private Long salaId;

    @Schema(description = "Nome da sala reservada", example = "Sala Amazônia")
    private String salaNome;

    @Schema(description = "Tipo da sala", example = "REUNIAO_COLETIVA")
    private String salaTipo;

    @Schema(description = "Responsável pela reserva", example = "João Silva")
    private String responsavel;

    @Schema(description = "Data e hora de início", example = "2025-06-15T09:00")
    private LocalDateTime inicio;

    @Schema(description = "Data e hora de fim", example = "2025-06-15T10:00")
    private LocalDateTime fim;

    @Schema(description = "Status da reserva", example = "ATIVA")
    private StatusReserva status;

    public ReservaResponseDTO() {}

    public ReservaResponseDTO(Reserva reserva) {
        this.id = reserva.getId();
        this.salaId = reserva.getSala().getId();
        this.salaNome = reserva.getSala().getNome();
        this.salaTipo = reserva.getSala().getTipo().name();
        this.responsavel = reserva.getResponsavel();
        this.inicio = reserva.getInicio();
        this.fim = reserva.getFim();
        this.status = reserva.getStatus();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getSalaId() { return salaId; }
    public void setSalaId(Long salaId) { this.salaId = salaId; }

    public String getSalaNome() { return salaNome; }
    public void setSalaNome(String salaNome) { this.salaNome = salaNome; }

    public String getSalaTipo() { return salaTipo; }
    public void setSalaTipo(String salaTipo) { this.salaTipo = salaTipo; }

    public String getResponsavel() { return responsavel; }
    public void setResponsavel(String responsavel) { this.responsavel = responsavel; }

    public LocalDateTime getInicio() { return inicio; }
    public void setInicio(LocalDateTime inicio) { this.inicio = inicio; }

    public LocalDateTime getFim() { return fim; }
    public void setFim(LocalDateTime fim) { this.fim = fim; }

    public StatusReserva getStatus() { return status; }
    public void setStatus(StatusReserva status) { this.status = status; }
}