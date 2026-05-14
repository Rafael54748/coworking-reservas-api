package com.desafio.reservas.application.dtos;

import com.desafio.reservas.domain.model.Reserva;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public class ReservaResponse {

    private Long id;
    private SalaResponse sala;
    private String responsavel;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Schema(type = "string", example = "2026-05-14T09:00")
    private LocalDateTime inicio;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Schema(type = "string", example = "2026-05-14T10:00")
    private LocalDateTime fim;

    private String status;

    public ReservaResponse() {}

    public ReservaResponse(Long id, SalaResponse sala, String responsavel,
                           LocalDateTime inicio, LocalDateTime fim, String status) {
        this.id = id;
        this.sala = sala;
        this.responsavel = responsavel;
        this.inicio = inicio;
        this.fim = fim;
        this.status = status;
    }

    public static ReservaResponse from(Reserva reserva) {
        return new ReservaResponse(
                reserva.getId(),
                SalaResponse.from(reserva.getSala()),
                reserva.getResponsavel(),
                reserva.getInicio(),
                reserva.getFim(),
                reserva.getStatus().name()
        );
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public SalaResponse getSala() { return sala; }
    public void setSala(SalaResponse sala) { this.sala = sala; }

    public String getResponsavel() { return responsavel; }
    public void setResponsavel(String responsavel) { this.responsavel = responsavel; }

    public LocalDateTime getInicio() { return inicio; }
    public void setInicio(LocalDateTime inicio) { this.inicio = inicio; }

    public LocalDateTime getFim() { return fim; }
    public void setFim(LocalDateTime fim) { this.fim = fim; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}