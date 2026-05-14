package com.desafio.reservas.application.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class ConsultarSalasLivresRequest {

    @NotNull(message = "O início é obrigatório.")
    @Future(message = "O início não pode ser no passado.")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime inicio;

    @NotNull(message = "O fim é obrigatório.")
    @Future(message = "O fim não pode ser no passado.")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fim;

    public ConsultarSalasLivresRequest() {}

    public LocalDateTime getInicio() { return inicio; }
    public void setInicio(LocalDateTime inicio) { this.inicio = inicio; }

    public LocalDateTime getFim() { return fim; }
    public void setFim(LocalDateTime fim) { this.fim = fim; }
}