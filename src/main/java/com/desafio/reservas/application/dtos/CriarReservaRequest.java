package com.desafio.reservas.application.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class CriarReservaRequest {

    @NotNull(message = "O id da sala é obrigatório.")
    private Long salaId;

    @NotBlank(message = "O nome do responsável é obrigatório.")
    private String responsavel;

    @NotNull(message = "O início é obrigatório.")
    @Future(message = "O início não pode ser no passado.")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Schema(type = "string", example = "2026-05-14T09:00")
    private LocalDateTime inicio;

    @NotNull(message = "O fim é obrigatório.")
    @Future(message = "O fim não pode ser no passado.")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Schema(type = "string", example = "2026-05-14T10:00")
    private LocalDateTime fim;

    public CriarReservaRequest() {}

    public Long getSalaId() { return salaId; }
    public void setSalaId(Long salaId) { this.salaId = salaId; }

    public String getResponsavel() { return responsavel; }
    public void setResponsavel(String responsavel) { this.responsavel = responsavel; }

    public LocalDateTime getInicio() { return inicio; }
    public void setInicio(LocalDateTime inicio) { this.inicio = inicio; }

    public LocalDateTime getFim() { return fim; }
    public void setFim(LocalDateTime fim) { this.fim = fim; }
}